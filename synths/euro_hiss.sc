{
var sss = BPF.ar(Splay.ar([WhiteNoise.ar,WhiteNoise.ar],0.4), MouseX.kr(2000,8000), MouseY.kr(0.01,1));
sss = sss * VarSaw.ar(2, width: XLine.ar(0.5,0.98,8)).exprange(0.0001,1);
}.play;
