s.options.memSize = 65536*8;
s.boot;

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

SynthDef(\reich, {
arg freq = 4, note = 50;
var tone,fade;
note = note - 12;
fade = LFSaw.kr(freq/2, iphase: pi/2);
tone = VOsc3.ar(LFNoise2.ar(1/8).range(0,7), note.midicps+[0,1],note.midicps+[0.37,1.1],note.midicps+[0.43, -0.29], 1/10);
tone = tone + Pulse.ar((note+LFNoise2.ar(1/10,-1/10)).midicps, width:LFNoise2.ar(1/16).range(0.5,0.4), mul:0.08);
//tone = [DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0,0.012)), DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0.012,0))];
//tone = tone * SinOsc.ar(freq, phase: XFade2.kr(LFNoise2.kr(1/10).range(0,pi*(3/2)),pi/2,fade.range(1,0))).range(1,fade.range(0.3,1));
Out.ar(0, MoogLadder.ar(tone,LFNoise2.ar(1/8).range(100,10000),0.05));
}).store();

{SinOsc.ar((50+5-12).midicps/2).dup * 0.3}.play;

~filt.free;
Synth(\reich, [\note, 50+12, \freq, 4]);
Synth(\reich, [\note, 50+12, \freq, 4]);
Synth(\reich, [\note, 54+12, \freq, 4]);
Synth(\reich, [\note, 57+12, \freq, 4]);
Synth(\reich, [\note, 59+12, \freq, 4]);
Synth(\reich, [\note, 50-12, \freq, 4]);
Synth(\reich, [\note, 50+12, \freq, 4]);
Synth(\reich, [\note, 54+12, \freq, 4]);
Synth(\reich, [\note, 57, \freq, 4]);
Synth(\reich, [\note, 59+12, \freq, 4]);

Synth(\reich, [\note, 50+12+4, \freq, 4]);
Synth(\reich, [\note, 50+12+4+5, \freq, 4]);
Synth(\reich, [\note, 50+12+4+5+5, \freq, 4]);
Synth(\reich, [\note, 50+12+4+5+5+5, \freq, 4]);

Synth(\reich, [\note, 50+12+4, \freq, 4]);
Synth(\reich, [\note, 50+4+5, \freq, 4]);
Synth(\reich, [\note, 50+12+4+5+5, \freq, 4]);
Synth(\reich, [\note, 50+12+4+5+5+5, \freq, 4]);

Synth(\reich, [\note, 50+4-2, \freq, 4]);
Synth(\reich, [\note, 50+12+4+5-2, \freq, 4]);
Synth(\reich, [\note, 50+12+4+5+5-2, \freq, 4]);
Synth(\reich, [\note, 50+12+4+5+5+5-2, \freq, 4]);

{
var tone,freq;
freq = 4;
tone = Pulse.ar(50.midicps/2, width: LFNoise2.ar(1/3).range(0.5,0.6)) * 1/10;
tone = [DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0,0.012)), DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0.012,0))];
tone = tone * SinOsc.ar(freq, phase: XFade2.kr(LFNoise2.kr(1/10).range(0,(1/4)*pi),pi/2,LFSaw.kr(freq/8, iphase: pi/2).range(1,0)));
Out.ar(0, tone);
}.play;

{
var tone,freq;
freq = 4;
tone = Pulse.ar(50.midicps*2, width: LFNoise2.ar(1/3).range(0.5,0.6)) * 1/10;
tone = [DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0,0.012)), DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0.012,0))];
tone = tone * SinOsc.ar(freq, phase: XFade2.kr(LFNoise2.kr(1/10).range(0,(1/4)*pi),pi/2,LFSaw.kr(freq/8, iphase: pi/2).range(1,0)));
}.play;

{
var tone,freq;
freq = 4;
tone = Pulse.ar(54.midicps*2, width: LFNoise2.ar(1/3).range(0.5,0.6)) * 1/10;
tone = [DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0,0.012)), DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0.012,0))];
tone = tone * SinOsc.ar(freq, phase: XFade2.kr(LFNoise2.kr(1/10).range(0,(1/4)*pi),pi/2,LFSaw.kr(freq/8, iphase: pi/2).range(1,0)));
Out.ar(0, tone);
}.play;

{
var tone,freq;
freq = 4;
tone = Pulse.ar(57.midicps*2, width: LFNoise2.ar(1/3).range(0.5,0.6)) * 1/10;
tone = [DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0,0.012)), DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0.012,0))];
tone = tone * SinOsc.ar(freq, phase: XFade2.kr(LFNoise2.kr(1/10).range(0,(1/4)*pi),pi/2,LFSaw.kr(freq/8, iphase: pi/2).range(1,0)));
Out.ar(0, tone);
}.play;

{
var tone,freq;
freq = 4;
tone = Pulse.ar(59.midicps*2, width: LFNoise2.ar(1/3).range(0.5,0.6)) * 1/10;
tone = [DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0,0.012)), DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0.012,0))];
tone = tone * SinOsc.ar(freq, phase: XFade2.kr(LFNoise2.kr(1/10).range(0,(1/4)*pi),pi/2,LFSaw.kr(freq/8, iphase: pi/2).range(1,0)));
Out.ar(0, tone);
}.play;

{
var tone,freq;
freq = 4;
tone = Pulse.ar(50.midicps, width: LFNoise2.ar(1/3).range(0.5,0.6)) * 1/10;
tone = [DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0,0.012)), DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0.012,0))];
tone = tone * SinOsc.ar(freq, phase: XFade2.kr(LFNoise2.kr(1/10).range(0,(1/4)*pi),pi/2,LFSaw.kr(freq/8, iphase: pi/2).range(1,0)));
Out.ar(0, tone);
}.play;

{
var tone,freq;
freq = 4;
tone = Pulse.ar(54.midicps, width: LFNoise2.ar(1/3).range(0.5,0.6)) * 1/10;
tone = [DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0,0.012)), DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0.012,0))];
tone = tone * SinOsc.ar(freq, phase: XFade2.kr(LFNoise2.kr(1/10).range(0,(1/4)*pi),pi/2,LFSaw.kr(freq/8, iphase: pi/2).range(1,0)));
Out.ar(0, tone);
}.play;

{
var tone,freq;
freq = 4;
tone = Pulse.ar(57.midicps, width: LFNoise2.ar(1/3).range(0.5,0.6)) * 1/10;
tone = [DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0,0.012)), DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0.012,0))];
tone = tone * SinOsc.ar(freq, phase: XFade2.kr(LFNoise2.kr(1/10).range(0,(1/4)*pi),pi/2,LFSaw.kr(freq/8, iphase: pi/2).range(1,0)));
Out.ar(0, tone);
}.play;

