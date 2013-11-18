Pbind(
\instrument, \pip,
\degree, Pstep(Pn(Pseries(0,Pseq([-4,3],inf), 8)), 2, inf) + [-7,0,2,4],
\octave, 4,
\strum, 0,
//\dur, Pseq([1,[3,3,[2,1!2]]].convertRhythm, inf) * 2
\dur, Pseq([1,[3,3,3,2]].convertRhythm, inf),
\scale, Scale.minor
).play;

TempoClock.default.tempo = 2

SynthDef(\pip, { | freq = 440 |
var sound = XLine.ar(1, 1/32, 1/2, doneAction: 2) * Splay.ar(LFPulse.ar(freq * [1,1.01,2.001,1/2,0.99], width: XLine.ar(0.0001, 1, 0.05) * XLine.ar(0.5,0.1,1)), 0.8) * 0.1;
Out.ar(0, sound);
}).add;

~echo = Synth(\dubecho, [\length, TempoClock.default.tempo*(3/8), \fb, 0.7, \sep, 0.0012], addAction: \addToTail);
~echo.free;

