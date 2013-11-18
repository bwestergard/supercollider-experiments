{
var in,mod,freq,wobble_freq,tone,sr;
wobble_freq = MouseY.kr(1/8,8) * 1.2;
freq = (47+12+0).midicps;
sr = 3000;
mod = SinOsc.ar(freq/2, mul: SinOsc.ar(wobble_freq).range(0.2,1.3), phase: LocalIn.ar);
tone = SinOsc.ar(freq, phase: mod);
LocalOut.ar(tone * 1.2 * SinOsc.ar(wobble_freq).range(0,1));
in = Decimator.ar(tone, 30000, 3);
//in = tone;
in = MoogLadder.ar(in, SinOsc.ar(wobble_freq, phase: pi/2).range(10000,freq*3), 0.35);
in = [DelayC.ar(in, 1, SinOsc.ar(wobble_freq/2).range(0,0.002)), DelayC.ar(in, 1, SinOsc.ar(wobble_freq/2).range(0.002,0))];
in = in + SinOsc.ar(freq/4).dup * LFTri.ar(wobble_freq).range(0.7,1.7);
Out.ar(0, in * 0.3);
}.play;
