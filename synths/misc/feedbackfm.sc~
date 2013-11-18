{
var tone,mod,freq,out;
freq = (57 + LFNoise2.ar(3).range(0,1/12)).midicps;
mod = LocalIn.ar * XLine.ar(MouseX.kr(0,3),1/100000,2);
tone = SinOsc.ar(freq, phase: mod) + VarSaw.ar(freq/2, mul: 2, width: XLine.ar(0.5,0.1,5)) + VarSaw.ar(freq*3, mul: XLine.ar(1,1/10000,2), width: XLine.ar(0.5,0.1,1));
LocalOut.ar(tone);
out = tone * (1/8) * XLine.ar(1,1/1000000,8) * XLine.ar(1/1000000,1,0.02);
out = MoogLadder.ar(out, Line.ar(freq,20000,0.15),0.1);
Out.ar(0, out.dup * 1);
}.play;
