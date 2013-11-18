SynthDef(\fm, {|note = 40, s = 0.01, f = 0.5, dur = 1, amp = 1, crushrate = 0.333|
var tone, mod;
note = note + (LFNoise2.ar(12).range(1,-1)*(1/3));
note = [note.midicps,note.midicps+XLine.ar(1/1000,Rand(1,2),dur/3)].cpsmidi;
mod = SinOsc.ar(note.midicps*Rand(2,3).round(1)*(1/Rand(1,2).round(1))) * XLine.ar(s,f,2*dur/3) * SinOsc.ar(Rand(1,8).round(1)/dur).range(1,0.8);
tone = SinOsc.ar(note.midicps, phase: mod);
tone = Decimator.ar(tone, XLine.ar(20000,note.midicps*2,dur*crushrate) * XLine.ar(1/10,1,dur/2), Line.ar(12,8,dur*crushrate));
tone = tone * XLine.ar(1,1/1000,dur*2,doneAction:2);
tone = tone * XLine.ar(1/1000,1,dur/64);
tone = amp * tone;
Out.ar(0, tone * 0.1);
}).store();

SynthDef(\zap, {|amp = 1, s = 20000, f = 10, t = 0.1|

Out.ar(0, amp * SinOsc.ar(XLine.ar(s,f,t,doneAction:2)).dup);
}).store();

Synth(\fm, [\note, 50-12]);
Synth(\fm, [\note, 53]);
Synth(\fm, [\note, 50+10]);
Synth(\fm, [\note, 65]);

var a = Pbind(
       \instrument, \fm,
       \note, 50+Pstep([[-12,3,7,10],[3-12,7,10,14,17]],2,inf),
       \crushrate, Pstep([1,2,3,0.8],1,inf),
       \strum, Prand([1/12,0,1/32],inf),
       \amp, 1,
       \s, 1/200,
       \f, 1,
       \dur, Pseq([1,1.5,1/8,1/8],inf)*2
       );
        

Ppar([a]).play;
