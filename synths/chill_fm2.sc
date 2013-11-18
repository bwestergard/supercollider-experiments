SynthDef(\fm, {|note = 40, s = 0.01, f = 0.5, dur = 1, coeff = 1.5|
var tone, mod;
//note = note + (LFNoise2.ar(12).range(1,-1)*(1/12));
note = [note.midicps,note.midicps+Rand(1,2)].cpsmidi;
mod = SinOsc.ar(note.midicps*coeff) * Line.ar(s,f,dur*2);
tone = SinOsc.ar(note.midicps, phase: mod);
tone = tone * XLine.ar(1,1/1000,dur,doneAction:2);
Out.ar(0, tone * 0.1);
}).store();

Synth(\fm, [\note, 40-12, \dur, 4, \s, 1/100, \f, 4, \coeff, 1/2]);
Synth(\fm, [\note, 40+3+12, \dur, 3, \s, 1/100, \f, 4, \coeff, 1/2]);
Synth(\fm, [\note, 47, \dur, 3, \s, 1/100, \f, 4, \coeff, 1/2]);

Pbind(
       \instrument, \fm,
       \note, Array.series(8,0,2).degreeToKey([0,2,5,7,9])+50+Pstep([0,0,0,0,-2,-2,-2,-7],1,inf),
       \strum, Pstep([0,Prand([1/64,1/16],1)],1/2,inf),
       \amp, 1/100,
       \s, 1/200,
       \f, Pwhite(0.1,1.1,inf).trace,
       \dur, Pseq([1/2,1/2,1,1,1/4,1/4,1/4,1/4],inf)*0.5
       ).play;
