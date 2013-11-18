{
var mod,tone,freq;
freq = (53).midicps;
mod = SinOsc.ar(freq*(3/2)) * 1;
mod = mod + LocalIn.ar;
tone = SinOsc.ar(freq, phase: mod);
LocalOut.ar(Decimator.ar(mod, 200000, SinOsc.ar(0.2, width: 0.95).range(1.1,1.5)));
tone = RLPF.ar(tone, XLine.ar(freq,10000,2), 0.2) * XLine.ar(1,1/100000,6);
Out.ar(0, tone.dup * 0.3);
}.play;
