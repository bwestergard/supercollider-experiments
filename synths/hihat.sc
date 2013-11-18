{
var dur = 0.1;
XLine.ar(1,1/10000,dur,doneAction:2) *
BPF.ar(WhiteNoise.ar,XLine.ar(10000,20000,dur), XLine.ar(0.5,0.9,dur)).dup
}.play;
