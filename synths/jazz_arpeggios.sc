SynthDef(\pluck, {|freq = 440, dur = 1|
var klang,note;
dur = dur * 2;
note = freq.cpsmidi;
note = note + (LFNoise2.ar(20).range(-1,1) * (1/8));
klang = SinOsc.ar((note * [1,1.002]).midicps, phase: VarSaw.ar(note.midicps, width: Line.ar(1,0.2,dur))) * 0.3;
klang = klang * XLine.ar(1,1/10000,dur,doneAction:2);
Out.ar(0, klang);
}).store;

Pbind(
\instrument, \pluck,
\degree, Pn(Pseries(0, Pwrand([Pwhite(-3,3,inf).round(1),1],[32,1],inf), 4),inf)-1 + [0,2,-1,-14],
\dur, Pseq([4, [5,2,1]].convertRhythm / 2, inf),
\strum, Pwhite(0,1/8,inf),
\octave, 5
).play;
