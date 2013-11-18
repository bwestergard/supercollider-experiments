SynthDef(\mallet,{arg out=0,freq=40,a=0.9,r=2;

         var sig= VarSaw.ar(freq*[3/2,1,1/2,2,1,3/2], width: LFNoise2.ar(1/3).exprange(0.5,0.1)) * 0.3;
	sig = Splay.ar(sig,0.8);
	//sig = RLPF.ar(sig, XLine.ar(0.1, 1, 0.001) * XLine.ar(4000,80,0.1));

Out.ar(out,sig*EnvGen.kr(Env.perc(a*4,r*2),gate:1,doneAction:2));
};
).store;

Pbind(
\instrument, \mallet,
\scale, Pstep([[0,4,7,9],[0,0,3,8],[0,0,5,8],[7,7,4,-12]],4,inf),
\degree, Pstep([[0,1,2],[2,3],[2,3,4],[0,2,-3,4,5,6]],1,inf).trace,
\a, 0.3,
\r, 0.8,
\dur, Pseq([4,[3,3,2]].convertRhythm,inf)
).play;

Pbind(
\instrument, \mallet,
\scale, Pstep([[0,4,7,9],[0,0,3,8],[0,0,5,8],[7,7,4,-12]],4,inf),
\degree, Pseq([
		Pshuf([0,1,2,3,0,0,0,-4],3),
		Pseg([0,2,2],1/2)
		],inf).trace,
\a, 0.01,
\r, 0.8,
\dur, Pseq([8,[1,2,1,1,[1,[1,3]],2]].convertRhythm
,inf) * 1/8,
).play;

// ConvertRhythm with built-in enveloping
