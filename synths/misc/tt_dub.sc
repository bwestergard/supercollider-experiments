s = Server.local;


s.sendMsg("/b_allocRead", 0, "/home/bjorn/audio_work/samples/j5.wav");

{
var index,out,freq,pos,seq,pulse,start_frame,stutter,seq3,in,tt_freq,foo,slow_pulse;

var notes = Dseq([30, 37, 33, 42, 30, 37, 33, 39], inf) + 8;
var wobble = Drand([2, 4, 3, 0.5, 6], inf);
in = AudioIn.ar(1);
tt_freq = ZeroCrossing.ar(in);
tt_freq = Lag.ar(tt_freq,0.003);
freq = 16 / BufDur.kr(0);
seq = Dseq([0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15], inf);
seq3 = Drand([1/3,-1/3,1/4,-1/4,0], inf);
pulse = Impulse.kr(freq);
pos = Demand.kr(pulse,0,seq);
start_frame = (pos * BufFrames.kr(0)) / 16;
stutter = MouseY.kr(0,4).round(1);
index = Phasor.ar(pulse, BufRateScale.kr(0) * LinLin.ar(tt_freq,0,1500,0,1.5), start_frame, start_frame + (BufFrames.kr(0) / (8 * (stutter ** 2))), start_frame);
out = BufRd.ar(1, 0, index,1,4) * 0.5;

slow_pulse = PulseDivider.kr(pulse, 4, 4);

foo = BMoog.ar(
		LFTri.ar(Lag.kr(Demand.kr(slow_pulse, 0, notes),0.05).midicps) * 0.8,
		LFTri.ar(Demand.kr(slow_pulse, 0, wobble)).abs * 1000 + 200, // cutoff freq.
		0.3, // q
		0, // mode - lowpass
		0.35); // mul

Out.ar(3,foo * 0.5);
Out.ar([0,1],Pan2.ar(out,Demand.kr(pulse,0,seq3)));
}.play;

