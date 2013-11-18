{
var tone,num,note,beat,compressed;
beat = AudioIn.ar(1);
note = 40-12-7;
num = 10;
tone = Silence.ar.dup;
num.do({
tone = tone + Pan2.ar(VarSaw.ar((note+LFNoise2.ar(1/2, Line.ar(1,1/12,1))).midicps * (Rand(1,3).round * 3/2), 0, LinLin.ar(LFNoise2.ar(8), -1, 1, 0.1, 0.8)), LFNoise2.ar(2));
});
compressed = Compander.ar(VarSaw.ar((MouseY.kr(60,24)).midicps, 0, LinLin.ar(LFNoise2.ar(1/3), -1, 1, 0.1, 0.8)).dup, beat, 0.2, 1, 1/40, 0.009, 1/6);
Out.ar(0, compressed * MouseX.kr(0,1));
}.play;
