SynthDef(\fm, {|note = 40, s = 0.01, f = 0.5, dur = 1|
var tone, mod;
//note = note + (LFNoise2.ar(12).range(1,-1)*(1/12));
note = [note.midicps,note.midicps+Rand(1,2)].cpsmidi;
mod = SinOsc.ar(note.midicps*Rand(2,3).round(1)*(1/Rand(1,2).round(1))) * XLine.ar(s,f,dur);
tone = SinOsc.ar(note.midicps, phase: mod);
tone = tone * XLine.ar(1,1/1000,dur*8,doneAction:2);
Out.ar(0, tone * 0.1);
}).store();

Synth(\fm, [\note, 50-12]);
Synth(\fm, [\note, 53]);
Synth(\fm, [\note, 50+10]);
Synth(\fm, [\note, 65]);

Pbind(
       \instrument, \fm,
       \note, Array.series(8,0,2).degreeToKey([0,2,5,7,9])+50+Pstep([0,0,0,0,-2,-2,-2,-7],1,inf),
       \amp, 1/100,
       \s, Pwhite(0.1,1.1,inf).trace,
       \f, 1/20,
       \dur, Pseq([1/2,1/2,1,1,1/4,1/4,1/4,1/4],inf)*0.5
       ).play;
