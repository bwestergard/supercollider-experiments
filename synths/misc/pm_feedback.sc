{
var in,out,tone,note;
note = 64 + LFNoise2.ar(0.3, 1/16);
in = SinOsc.ar(note.midicps, phase: LocalIn.ar());
LocalOut.ar(DelayC.ar(in, 1, LFNoise2.ar(0.1,0.005) + 0.01) * 0.8);
LocalOut.ar(DelayC.ar(in, 1, LFNoise2.ar(0.1,0.05) + 0.1) * 0.7);
LocalOut.ar(SinOsc.ar((note+7).midicps) * Line.ar(1,0,3));
LocalOut.ar(SinOsc.ar((note+12).midicps) * Line.ar(1,0,7));
out = in + LocalIn.ar();
out.dup * 0.05;
}.play;
