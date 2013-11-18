{
var tone,note,num;
num = 20;
note = 40+12;
tone = Silence.ar;
num.do({
tone = tone + Pan2.ar(VarSaw.ar((note.midicps + LFNoise2.ar(1/8, 1/12)) * Rand(1,4).round(1), 0, LFNoise2.ar(1/2).range(0.15, 0.5)), LFNoise2.ar(8).range(-1,1));
});
tone = tone * (1/num);
tone = Compander.ar(tone, tone, 1/10, 0.01, 0.01, 0.01, 0.01) * SinOsc.ar(LFNoise2.ar(1/30).range(0.1,1)).range(SinOsc.ar(LFNoise2.ar(1/30).range(0.1,4)).range(0.7,1),1);
Out.ar(0, tone);
}.play;

{
var in, wobble;
in = In.ar([0,1]);
wobble = LFTri.ar(2).range(100,15000);
in = BLowPass.ar(in, 200, 0.4);
ReplaceOut.ar(0, in);
}.play(RootNode(s), nil, \addToTail);
