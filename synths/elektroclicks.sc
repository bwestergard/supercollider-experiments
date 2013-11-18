{
var tone = BPF.ar(Impulse.ar(LFNoise0.ar(1).range(32,1).round(0.25)),MouseX.kr(20,20000), MouseY.kr(0.001,1)).dup;
tone = LPF.ar(tone,8000);
tone = Integrator.ar(tone, 0.1, 0.2);
tone = LeakDC.ar(tone);
tone = [DelayC.ar(tone, 1, LFNoise2.ar(8).range(0,0.0012)), DelayC.ar(tone, 1, LFNoise2.ar(8).range(0.0012,0))]
}.play;

Help.gui;
