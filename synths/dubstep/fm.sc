{
var note = 30;
var freq = note.midicps;
var wobble = 2;
var tone = SinOsc.ar(freq, phase: LocalIn.ar);
LocalOut.ar(VarSaw.ar(freq*(0.5), 0, LFTri.ar(wobble).range(0.001,0.5)) * LFTri.ar(wobble).range(0,1));
LocalOut.ar(SinOsc.ar(freq*3, mul: 0.4) * LFTri.ar(wobble).range(0,1));
LocalOut.ar(DelayC.ar(tone * Line.ar(0,0.3,5/3), 3, 1/(3*freq)));
Out.ar(0,tone.dup * 0.3);
}.play;
