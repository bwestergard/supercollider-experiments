play({
var out;
out = VarSaw.ar(
		(50+12+(LFNoise2.ar(32)/16)).midicps, 
		0, 
		LinLin.ar(SinOsc.ar(LFNoise2.ar(32.01)),-1,1,0.2,0.03), //width
		0.1) ;
[out,out]
});

