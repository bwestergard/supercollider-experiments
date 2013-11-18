SynthDef(\dess,
{
arg freq = 440;
var tone, env, envdef;
envdef = Env.new([0, 1, 0.9, 0], [0.5, 0.5, 1] * 0.3,[-5, 0, -5]);
env = XLine.ar(1,1/1000,0.1,doneAction:2);
tone = Pulse.ar(freq) * env * AmpComp.kr(freq);
Out.ar(0, tone.dup);
}).store;

Pdef(\leap,
	Pbind(
		\instrument, \dess,
		\dur, 4,
		\strum, 1/6,
		\degree, [0,7,12,24]
	)
).play;

SynthDef(\verb,{
var input = In.ar(0, 2);
var output = FreeVerb.ar(input, 0.3, 2, 0.1);
ReplaceOut.ar(0, RLPF.ar(output, MouseButton.kr(40,20000), 0.1));
}).store;

// Example Usage
~verb = Synth(\verb, addAction: \addToTail);
~verb.free;
