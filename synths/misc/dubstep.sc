{
	var foo;
	foo = SinOsc.ar(LFNoise2.ar(8).range(20,2000)) * 0.001;
	foo = Compander.ar(foo, foo, 0.5, 0.1, 0.1, 0.01, 0.01);
[foo,foo]
}.play
