{
Mix.fill(200, { Pan2.ar(SinOsc.ar(LFNoise2.ar(1/32).range(XLine.ar(20,150,15),XLine.ar(4000,2000,15))), LFNoise2.ar(0.3).range(-1,1)) / 200 }).dup;
}.play;
