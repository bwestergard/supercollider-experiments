(
var snare, kick, snare_part, clicks, swing, swing_off, high, bass;

TempoClock.default.tempo = 40/30;

snare = Buffer.read(s, "/home/bjorn/audio_work/samples/budos_snare.wav");
kick = Buffer.read(s, "/home/bjorn/audio_work/samples/electro_kick.wav");

snare_part = Pbind(
       \instrument, \drum_machine,
       \div, 16,
       \bjorklund, Pbjorklund(Prand([7,9,13,5],inf),Pkey(\div)),
       \bufnum, Pstep([kick.bufnum,snare.bufnum],1,inf),
       \dur, Pstep(Pseq([4,4,4,2],inf),2,inf)/Pkey(\div),
       \amp, Pkey(\bjorklund) * 0.15 * Pstep(Pseq([1,0.6],inf),1/6,inf) 
       );

clicks = Pbind(
       \instrument, \click,
       \amp, Pstep(Pseq([1,0.3],inf),1/6),
       \dur, Pstep(Pseq([1/4,1/4,1/4,Prand([1/8,1/6])],inf), 2)
       );
       
high = Pbind(
       \instrument, \sinepluck,
       \div, Pstep(Prand([8,16],inf),2,inf),
       \scale, Scale.major,
       \amp, (Pbjorklund(Prand([4,5,7],inf),Pkey(\div))) * 0.4 + 0.05,
       \dur, 1/(Pkey(\div)),
       \degree, Pstep([0,5],8,inf) + Pshuf([0,2,4,-1],inf),
       \out, 2,
       \octave, Pstep(Pseq([6,5],inf),1/2,inf)
);

bass = Pbind(\instrument, \ital_bass, \dur, 2, \octave, 3, \degree, Pseq([0,0,5,5],inf), \wobble, Prand([2/3,2,4,1/3],inf)*TempoClock.default.tempo );
       
Ptpar([0,high,16,clicks,48,bass,32,snare_part]).play;

)

SynthDef(\drum_machine, {| out = 0, bufnum = 0, rate = 1, amp = 1, dur = 1|
var playback;
playback = PlayBuf.ar(2, bufnum, BufRateScale.kr(bufnum), rate: rate, doneAction:2).dup * 0.8;
Out.ar(out, playback * amp)
}).store();

SynthDef(\click, {| out = 0, amp = 0, dur = 1 |
Out.ar(out, XLine.ar(1,1/1000,dur*2, doneAction:2) * BPF.ar(PinkNoise.ar,XLine.ar(20000,LinLin.kr(amp,0,1,200,1000),dur),XLine.ar(0.99,0.01,dur)).dup * 0.8 * amp);
}).store();

Synth(\click);

(
var kick,snare;
snare = Buffer.read(s, "/home/bjorn/audio_work/samples/budos_snare.wav");
kick = Buffer.read(s, "/home/bjorn/audio_work/samples/electro_kick.wav");
Synth(\drum_machine, [\bufnum, kick.bufnum, \out, 0, \amp, 0.5]);
)
{SinOsc.ar}.play;

(
SynthDef("sinepluck", {
arg freq = 440, amp = 1, dur = 1, detune = 1/4, out = 0;
var mod,tone;
dur = dur;
amp = amp * 0.8;
mod = VarSaw.ar(freq*2, mul: XLine.ar(0.2,0.8,dur/2), width: XLine.ar(1,1/1000,dur*64));
tone = SinOsc.ar(freq, mod).dup * 0.1 * XLine.ar(1,1/1000,dur*4, doneAction:2) * XLine.ar(1/1000,1,0.001) * amp;
tone = [DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0,0.012)), DelayC.ar(tone , 1, LFNoise2.ar(1/2).range(0.012,0))];
tone = tone * XLine.ar(1/10000,1,0.005);
Out.ar(out,tone);
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

Help.gui;
