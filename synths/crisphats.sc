{
var f = WhiteNoise.ar;
f = f + AllpassC.ar(f,0.1,0.001*(1+(LFNoise2.ar(1/8).range(-1,1)*0.06)).poll, 0);
f = HPF.ar(f, 8000, 2);
f = f * EnvGen.kr(Env.perc(0.0001,MouseX.kr(0.03,0.8)), LFPulse.ar(6, 0.01)) * MouseX.kr(0.6,1);
f.dup / 2;
}.play;
