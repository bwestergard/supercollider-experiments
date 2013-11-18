(
s = Server.local;
// allocate and fill tables 0 to 7
8.do({ arg i;
	var n, a;
	// allocate table
	s.sendMsg(\b_alloc, i, 1024); 
	// generate array of harmonic amplitudes
	n = (i+1)**2;
	a = Array.fill(n, { arg j; ((n-j)/n).squared.round(0.001) });
	// fill table
	s.performList(\sendMsg, \b_gen, i, \sine1, 7, a);
});
)

SynthDef("help-VOsc",{ arg out=0, bufoffset=0;
	var freq = (40-12).midicps;
	var t = 1;
	var foo = VOsc3.ar(bufoffset+XLine.ar(2,0.1,t), freq+[0,0.01],freq+[0.2,1.1],freq+[0.43, -0.29], 0.3) * XLine.ar(1,1/10000,t, doneAction: 2);
	// mouse x controls the wavetable position
	foo = MoogLadder.ar(foo,XLine.ar(20000,40,t*0.2),0.1);
	Out.ar(out,
		foo)
}).play(s,[\out, 0, \bufoffset, 0]);
