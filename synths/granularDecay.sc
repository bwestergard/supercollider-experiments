(
{
	var freq = 50.midicps;
	var rate = XLine.ar(10, 800, 10);
	var eps = 0.2;
	var trig = Impulse.ar(rate * LFNoise2.ar(1/3).range(1-eps,1+eps));
	var freqs = Drand([-12,-24,12,24,12+4,7+12,9+12,9-12].midiratio, inf) * freq;
	Out.ar(0,
		Decay.ar(trig, 0.1) * GrainSin.ar(2, trig, 100/rate, Demand.ar(trig, 0, freqs), LFNoise0.ar(20000).range(-1,1), -1)) * 0.00000001
}.play
)
