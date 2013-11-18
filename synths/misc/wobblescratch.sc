{
	var in,tt_freq,pulse,notes,wobble,foo,freq;
	in = AudioIn.ar(1);
	tt_freq = ZeroCrossing.ar(in);
	tt_freq = Lag.ar(tt_freq,0.03);
	pulse = Impulse.ar(2);
	notes = Dseq([30, 37, 33, 42, 30, 37, 33, 39], inf) +0;
	wobble = Drand([2, 4, 3, 6, 0.5, 0.3], inf);
	freq = Lag.ar(Demand.ar(pulse, 0, notes),0.2).midicps;
	foo = BMoog.ar(
		LFTri.ar(freq) * 0.8,
		Clip.ar(LinLin.ar(tt_freq,200,1500,freq * 1,freq * 8),100,2000), // cutoff freq.
		0.8, // q
		0, // mode - lowpass
		0.35); // mul
[foo,foo]
}.play
