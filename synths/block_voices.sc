{
var freq = LFNoise2.ar(1/8).range(0.2,0.1);
n = LPF.ar(HPF.ar([PinkNoise.ar,PinkNoise.ar], 300), 3400) * SinOsc.ar(freq).exprange(0.8,1);
n * 5;
}.play;
