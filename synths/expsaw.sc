// Plot of this looks really cool!

{
var tone = Splay.ar(
LeakDC.ar(VarSaw.ar(160 * [1,1.01,0.988,1.02] * 3.midiratio, width: SinOsc.ar(1/3).exprange(0.01,0.5)).exprange(
	1e-1 // This parameter is key.
,1)),
1);
//tone = RLPF.ar(tone, Saw.ar(8).exprange(20,20000));
tone;
}.play;
