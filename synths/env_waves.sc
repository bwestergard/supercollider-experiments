{
var freq = 90 * [1,1.0001,1.002,0.998,0.999];
var tone = Splay.ar(EnvGen.ar(Env.perc(0.01, 0.001, 1, -4), Impulse.ar(freq)), 0.3);
tone = RLPF.ar(tone, XLine.ar(20000,20,0.8), 0.8) * XLine.ar(1,1/1000,0.5);
}.play
