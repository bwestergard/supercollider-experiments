{
var in,mod,freq,wobble_freq,tone;
wobble_freq = 2;
freq = (43+12+12).midicps;
mod = SinOsc.ar(freq/2, mul: SinOsc.ar(wobble_freq).range(0.2,1.3), phase: LocalIn.ar);
tone = SinOsc.ar(freq, phase: mod);
LocalOut.ar(tone * 1.2 * SinOsc.ar(wobble_freq).range(0,1));
in = Decimator.ar(tone, 800000, 3);
//in = tone;
in = MoogLadder.ar(in, SinOsc.ar(wobble_freq, phase: pi/2).range(8000,freq), 0.35);
in = [DelayC.ar(in, 1, SinOsc.ar(wobble_freq/2).range(0,0.002)), DelayC.ar(in, 1, SinOsc.ar(wobble_freq/2).range(0.002,0))];
in = in + SinOsc.ar(freq/4).dup * SinOsc.ar(wobble_freq).range(XLine.ar(0.5,1/1000,10),1.7);
in = in * XLine.ar(1,1/10000, 4);
Out.ar(0, in * 0.7);
}.play;
