{
var tone,note,time,clip;
clip = 20;
time = 3;
note = 30;

tone = LFTri.ar(note.midicps);
tone = tone + Pulse.ar(note.midicps, width: LFNoise2.ar(1/2).range(0.5,0.6));
tone = tone / 2;
tone = tone * SinOsc.ar(note.midicps/2).range(MouseY.kr(0,1),1);
tone = MoogLadder.ar(tone, XLine.ar(10000,10,0.20*time), XLine.ar(0.1,0.8,0.1*time)) * XLine.ar(1,1/1000,0.6*time);
tone = (tone*clip).tanh/clip;
tone = MoogLadder.ar(tone, 8000,0.4);
tone = LeakDC.ar(tone);
tone.dup;
}.play;
