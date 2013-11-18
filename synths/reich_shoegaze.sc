{
var tone,freq;
freq = 4;
tone = Pulse.ar(50.midicps/2, width: LFNoise2.ar(1/3).range(0.5,0.6)) * 1/10;
tone = [DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0,0.012)), DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0.012,0))];
tone = tone * SinOsc.ar(freq, phase: XFade2.kr(LFNoise2.kr(1/10).range(0,2*pi),pi/2,LFSaw.kr(freq/4, iphase: pi/2).range(1,0)));
Out.ar(0, tone);
}.play;

{
var tone,freq;
freq = 4;
tone = Pulse.ar(50.midicps*2, width: LFNoise2.ar(1/3).range(0.5,0.6)) * 1/10;
tone = [DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0,0.012)), DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0.012,0))];
tone = tone * SinOsc.ar(freq, phase: XFade2.kr(LFNoise2.kr(1/10).range(0,2*pi),pi/2,LFSaw.kr(freq/4, iphase: pi/2).range(1,0)));
}.play;

{
var tone,freq;
freq = 4;
tone = Pulse.ar(54.midicps*2, width: LFNoise2.ar(1/3).range(0.5,0.6)) * 1/10;
tone = [DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0,0.012)), DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0.012,0))];
tone = tone * SinOsc.ar(freq, phase: XFade2.kr(LFNoise2.kr(1/10).range(0,2*pi),pi/2,LFSaw.kr(freq/4, iphase: pi/2).range(1,0)));
Out.ar(0, tone);
}.play;

{
var tone,freq;
freq = 4;
tone = Pulse.ar(57.midicps*2, width: LFNoise2.ar(1/3).range(0.5,0.6)) * 1/10;
tone = [DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0,0.012)), DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0.012,0))];
tone = tone * SinOsc.ar(freq, phase: XFade2.kr(LFNoise2.kr(1/10).range(0,2*pi),pi/2,LFSaw.kr(freq/4, iphase: pi/2).range(1,0)));
Out.ar(0, tone);
}.play;

{
var tone,freq;
freq = 4;
tone = Pulse.ar(59.midicps*2, width: LFNoise2.ar(1/3).range(0.5,0.6)) * 1/10;
tone = [DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0,0.012)), DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0.012,0))];
tone = tone * SinOsc.ar(freq, phase: XFade2.kr(LFNoise2.kr(1/10).range(0,2*pi),pi/2,LFSaw.kr(freq/4, iphase: pi/2).range(1,0)));
Out.ar(0, tone);
}.play;

{
var tone,freq;
freq = 4;
tone = Pulse.ar(50.midicps, width: LFNoise2.ar(1/3).range(0.5,0.6)) * 1/10;
tone = [DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0,0.012)), DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0.012,0))];
tone = tone * SinOsc.ar(freq, phase: XFade2.kr(LFNoise2.kr(1/10).range(0,2*pi),pi/2,LFSaw.kr(freq/4, iphase: pi/2).range(1,0)));
Out.ar(0, tone);
}.play;

{
var tone,freq;
freq = 4;
tone = Pulse.ar(54.midicps, width: LFNoise2.ar(1/3).range(0.5,0.6)) * 1/10;
tone = [DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0,0.012)), DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0.012,0))];
tone = tone * SinOsc.ar(freq, phase: XFade2.kr(LFNoise2.kr(1/10).range(0,2*pi),pi/2,LFSaw.kr(freq/4, iphase: pi/2).range(1,0)));
Out.ar(0, tone);
}.play;

{
var tone,freq;
freq = 4;
tone = Pulse.ar(57.midicps, width: LFNoise2.ar(1/3).range(0.5,0.6)) * 1/10;
tone = [DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0,0.012)), DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0.012,0))];
tone = tone * SinOsc.ar(freq, phase: XFade2.kr(LFNoise2.kr(1/10).range(0,2*pi),pi/2,LFSaw.kr(freq/4, iphase: pi/2).range(1,0)));
Out.ar(0, tone);
}.play;

