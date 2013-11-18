Pbind(
\instrument, \pip,
\degree, Pn(Pfinval(Pseq([8,3,3,2],inf).asStream, Pseries(0,Pseq([2,2,2,Pwhite(1,4)],inf))),inf).trace + Pstep([0,-2,3,4],1,inf),
\dur, 1 / 8,
\octave, 5,
\legato, 2,
\amp, 1
).play;

~echo = Synth(\dubecho, [\length, TempoClock.default.tempo*(3/8), \fb, 0.7, \sep, 0.0012], addAction: \addToTail);


SynthDef(\pip, { | freq = 440, sustain = 1, amp = 1 |
Out.ar(0, XLine.ar(1,1/1000, sustain * 2, doneAction: 2) * Splay.ar(SinOsc.ar(freq * [0.99,2.001,1]) ,1) * 0.2 * amp);
}).add;
