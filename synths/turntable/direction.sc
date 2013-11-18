{
var a = (Schmidt.ar(SoundIn.ar([0]), 0, 0) - 0.5) * 2;
var b = (Schmidt.ar(SoundIn.ar([1]), 0, 0) - 0.5) * 2;
var direction = Latch.ar(a,b);
var freq = ZeroCrossing.ar(SoundIn.ar([0])) * direction;
}.play;
