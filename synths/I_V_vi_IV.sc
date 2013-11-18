Pdef(\foo,
	Pbind(
	\degree, (Pseq([0,4,5,3,1], inf).trace + [0,2-7,4,-1]),
	\octave, [3,4] + 1,
	\strum, Pstep([0,Prand([1/32,0])],2,inf),
	\dur, Pseq([1,1,1,0.5,0.5],inf),
	\legato, 0.96
	)
).play();
