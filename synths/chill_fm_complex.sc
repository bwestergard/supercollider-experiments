SynthDef(\fm, {|note = 40, s = 0.01, f = 0.5, dur = 1, amp = 1, crushrate = 8, warble = 1e-100|
var tone, mod;
note = note + (LFNoise2.ar(16).range(1,-1)*warble);
note = [note.midicps,note.midicps+XLine.ar(1/10000,Rand(1,2),dur/3)].cpsmidi;
mod = SinOsc.ar(note.midicps*Rand(2,7).round(1)*(1/Rand(1,2).round(1))) * XLine.ar(s,f,2*dur/3) * SinOsc.ar(Rand(1,20).round(1)/dur).range(1,0);
tone = SinOsc.ar(note.midicps, phase: mod);
tone = Decimator.ar(tone, XLine.ar(20000,note.midicps*2,dur*crushrate) * XLine.ar(1/2,1,dur/2), Line.ar(12,8,dur*crushrate));
tone = tone * XLine.ar(1,1/1000,dur*2,doneAction:2);
tone = tone * XLine.ar(1/1000,1,dur/64);
tone = amp * tone;
Out.ar(0, tone * 0.1);
}).store();

SynthDef(\fm, {|note = 40, s = 0.01, f = 0.5, dur = 1, coeff = 1.5| // alternative
var tone, mod;
//note = note + (LFNoise2.ar(12).range(1,-1)*(1/12));
note = [note.midicps,note.midicps+Rand(1,2)].cpsmidi;
mod = SinOsc.ar(note.midicps*coeff) * Line.ar(s,f,dur*2);
tone = SinOsc.ar(note.midicps, phase: mod);
tone = tone * XLine.ar(1,1/1000,dur,doneAction:2);
Out.ar(0, tone * 0.1);
}).store();

Synth(\fm, [\note, 50-12]);
Synth(\fm, [\note, 53]);
Synth(\fm, [\note, 50+10]);
Synth(\fm, [\note, 65]);

var a = Pbind(
       \instrument, \fm,
       \note, 50+Pstep([[3,-12,7,10],[3-12,7,10,14,17],[3,-12,7,10],7+[0,3-12,7,10-12]],2,inf)+[0,12,12,12],
       \crushrate, Pstep([32,2,3,1],1,inf),
       \strum, Prand([1/12,1/32,0.0001,1/16],inf),
       \warble, Pwrand([1/100000,1/12,1/3],[3,1,1].normalizeSum,inf).trace,
       \amp, 1,
       \s, 1/200,
       \f, 1,
       \dur, Pseq([1,1.5,1/8,1/8],inf)*2
       );
        

Ppar([a]).play;
