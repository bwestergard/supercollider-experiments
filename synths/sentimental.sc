(
var snare, kick, alt_kick, snare_part, clicks, alternate, ping;

TempoClock.default.tempo = 1;

snare = Buffer.read(s, "/home/bjorn/audio_work/samples/budos_snare.wav");
kick = Buffer.read(s, "/home/bjorn/audio_work/samples/kick.wav");
alt_kick = Buffer.read(s, "/home/bjorn/audio_work/samples/kraft_prog.wav");

alternate = Pclutch(Pseq([kick.bufnum,snare.bufnum],inf), Pbjorklund(Prand([13,5],inf),16));

snare_part = Pbind(
       \instrument, \drum_machine,
       \div, 16,
       \bjorklund, Pbjorklund(5,Pkey(\div)),
       \bufnum, Pstep(alternate,1/16),
       \dur, 2/Pkey(\div),
       \amp, ((Pkey(\bjorklund) * 0.15 * Pstep(Pseq([1,0.6],inf),1/8,inf)) + 0.01) * 0.8
       );

clicks = Pbind(
       \instrument, \click,
       \amp, Pstep(Pseq([1,0.3],inf),1/6) * 0.9,
       \dur, Pstep(Pseq([1/4,1/4,1/4,Prand([1/6,1/8])],inf), 1)
       );
       
ping = Pbind(
       \instrument, \sinepluck,
       \amp, 0.3,
       \degree, Pseq([0,4],inf) + [0,0,0,-2,4,7,-7],
       \octave, 6,
       \dur, 2
       );

Ppar([snare_part,ping]).play;

)

Help.gui;


SynthDef(\drum_machine, {| out = 0, bufnum = 0, rate = 1, amp = 1, dur = 1,sep = 0|
var playback;
dur = dur * 3;
playback = PlayBuf.ar(1, bufnum, BufRateScale.kr(bufnum), rate: rate).dup * 0.8 * XLine.ar(1,1/2,dur, doneAction:2) * XLine.ar(1/1000,1,0.001);
//playback = MoogLadder.ar(playback, XLine.ar(4000,200,dur), 0.3) * 4;
playback = [DelayC.ar(playback, 1, LFNoise2.ar(1/2).range(0,sep)), DelayC.ar(playback, 1, LFNoise2.ar(1/2).range(sep,0))];
Out.ar(out, playback * amp * MouseX.kr(0,1))
}).store();

/*
SynthDef(\drum_machine, {| out = 0, bufnum = 0, rate = 1, amp = 1, dur = 1,sep = 0|
var playback;
playback = PlayBuf.ar(1, bufnum, BufRateScale.kr(bufnum), rate: rate * Rand(1,2).round(1), doneAction:2).dup * 0.8 * XLine.ar(2,1,8*dur, doneAction:2);
playback = [DelayC.ar(playback, 1, LFNoise2.ar(1/2).range(0,sep)), DelayC.ar(playback, 1, LFNoise2.ar(1/2).range(sep,0))];
Out.ar(out, playback * amp)
}).store();
*/

SynthDef(\click, {| out = 0, amp = 0, dur = 1 |
Out.ar(out, XLine.ar(1,1/1000,dur*2, doneAction:2) * BPF.ar(PinkNoise.ar,XLine.ar(20000,LinLin.kr(amp,0,1,200,1000),dur),XLine.ar(0.99,0.01,dur)).dup * 0.8 * amp);
}).store();

(
SynthDef("sinepluck", {
arg freq = 440, amp = 1, dur = 1, detune = 1/4, out = 0;
var mod,tone;
freq = Rand(0.99,1.01) * freq;
dur = dur * 4;
freq = freq * Pulse.ar(dur/6).range(0.5,2);
tone = (LFSaw.ar(freq/LFNoise0.ar(dur/6).range(4,2).round(1))*1.3 + LFSaw.ar((freq.cpsmidi + LFNoise2.ar(3).range(1/12,-1/12)).midicps))/2;
//tone = VarSaw.ar(dur/4, width: 0.1).range(0.1,1) * tone;
tone = tone * 0.1 * amp;
tone = tone * XLine.ar(1,1/1000,dur, doneAction:2);
Out.ar(out,tone.dup);
}).store;
)

(
SynthDef("ital_bass", {
arg freq = 440, amp = 1, dur = 1, detune = 1/4, out = 0, wobble = 1;
var mod,tone,foo;
tone = VarSaw.ar(Lag.kr(freq), width:LFNoise2.ar(3).range(0.3,0.1));
tone = MoogLadder.ar(tone, SinOsc.ar(wobble).range(20,4000), 0.1) * 4;
tone = tone * XLine.ar(8,1/1000,dur, doneAction:2).tanh;
Out.ar(out,tone.dup * amp);
}).store;
)
