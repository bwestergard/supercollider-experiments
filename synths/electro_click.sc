{
XLine.ar(1,1/10000000,0.1, doneAction:2) * SinOsc.ar(Saw.ar(2).range(100,20000-100)).dup
}.play;
