{
var freqs, sound, t;
t = 8;
freqs = 40 + 12 + [-24+8,3,12,7] + (LFNoise2.ar(1.dup(10)).range(-1,1) * (1/8)); // [-24,3,10,7], [-24+7,3,10,7], [-24+8,3,12,7]
freqs = freqs.midicps;
sound = Fold.ar(VarSaw.ar(freqs, width: LFNoise2.ar(3).exprange(0.5,1)), LFNoise2.ar(1,0.5,-0.5), LFNoise2.ar(0.8,0.1,0.5)) * 0.7;
sound = Splay.ar(sound, 0.8);
sound = LeakDC.ar(sound);
sound = RLPF.ar(sound, XLine.ar(10000,20000, 0.1 * t), 0.3) * XLine.ar(0.001, 1, t/128) * XLine.ar(1,1/10000, t * 0.3, doneAction:2);
sound;
}.play;
