// Funky Selector Molester (-by raja © 2012 <-;p ;D)
(
TempoClock.tempo = (40/60);
SynthDef(\kik, { |preamp = 1, amp = 1, curve, kiklen|
    var freq = EnvGen.kr(Env([150, 50, 35, 25], [0.001, 0.2, 0.4], curve),
timeScale: kiklen, doneAction: 2),
        sig =
                        RLPF.ar(
                        LFTri.ar(freq, 0.5pi * LFSaw.ar(Line.kr(150, 30, 0.05), 0.75pi),
preamp).ring1(100).distort.clip2.tanh * amp
            * EnvGen.kr(Env([0, 1, 0.5, 0], [0.001, 0.2, 0.4]), timeScale:
kiklen),
                        Line.kr(300, 25, 0.05), Line.kr(0.01, 0.5, 0.05));
    Out.ar(0, sig ! 2);
}).add;

SynthDef(\snare, { |amp = 1, envlp, phase|

        var snareosc, snarenv, snarenoise;
        snarenv = EnvGen.kr(Env.perc(0.001, 0.5, 2, envlp), doneAction: 2);
        snarenoise = PinkNoise.ar * LFTri.ar(SinOsc.ar(35, mul: 400, add: 800),
phase);
        snareosc = RHPF.ar(snarenoise, Line.kr(500, 1000, 0.1), Line.kr(0.1, 2,
0.15)).distort.tanh;
        Out.ar(0, (snareosc*snarenv*amp) ! 2);

}).add;

SynthDef(\closedhat, {

    var hatosc, hatenv, hatnoise, hatoutput;

    hatnoise = {RLPF.ar(WhiteNoise.ar(1),Line.kr(6000, 50, 0.015)*2,
Line.kr(0.1, 0.5, 0.015))};

    hatosc = {RHPF.ar(hatnoise, Line.kr(1000, 5000, 0.015), Line.kr(0.1, 2,
0.015))};
    hatenv = EnvGen.kr(Env.perc(0.001, 1, 1, -8), doneAction: 2);

    hatoutput = (hatosc*hatenv).distort.tanh;

    Out.ar(0,
    Pan2.ar(hatoutput, 0)
    )

}).add;

SynthDef(\oscirator1,
        { |freq, len|
        var osc, env, mod, filter, filterenv;

        env = EnvGen.kr(Env([0,0.8,0.5,0.3,0], [0.01, 0.1, 0.04, (len-0.15)], -4),
doneAction: 2);
        filter = VarSaw.ar(freq) * env;

        Out.ar(0, filter!2);
        }).add;

SynthDef(\oscirator2,
        { |freq, len|
        var osc, env, mod, filter, filterenv, out;

        env = EnvGen.kr(Env.triangle(len*2, 0.8), doneAction: 2);
        mod = VarSaw.ar((env**8)+freq, 0.5);
        osc = SinOscFB.ar(freq, mod);
        filterenv = EnvGen.kr(Env.sine(len+0.4, 1));
        filter = RLPF.ar(osc, (filterenv**4)+freq,
0.3).ring2(Line.kr(SinOsc.kr(0.1,0,1.1, 1.2),0,len*2)).tanh;

        out = Pulse.ar(freq, width: 0.3)*env* 0;

        Out.ar(0, out!2);
        }).add;


SynthDef(\echo, { arg delay = 0.2, decay = 4, amp = 1, dry = 0.5;
var in, wet, out;
in = In.ar(0, 2);
wet = (in*dry) * amp;
out = AllpassC.ar((BPF.ar(wet, 600, 0.9) * 4).distort, 1, Lag.kr(delay, 1), decay, 1, in);
ReplaceOut.ar(0, out);
}).add;

n = Pbind(\instrument, \oscirator1,
                \delta, 0.125,
                \midinote, Pseq([Pseq([36, \rest, \rest, \rest, 44, 42, 44, 38, \rest,
\rest, \rest, 42, \rest, 38, \rest, \rest], 16), Pseq([36, \rest, 36, \rest,
34, 34, \rest, 34, 33,  \rest, \rest, \rest, 32, \rest, \rest, 34], 4)],
inf),
                \len, 0.25).play(quant:2);

o = Pbind(\instrument, \oscirator2,
                \delta, 0.125,
                \midinote, Pseq([36, 42, 44, 39, 46, 48, 54, 51], inf) + Pxrand([12, 24,
24, 12, 36], inf),
                \len, 0.3).play(quant:4);

k = Pbind( \instrument, \kik,
\delta, Pseq([1, 0.125, 0.25, 0.125, 0.5], inf),
\preamp, Pseq([4, 1, 2, 3, 1], inf),
\curve, Pseq([-2, -4, -8, -16, -2],inf),
\kiklen, Pseq([1, 0.1, 0.5, 0.1, 0.4],inf),
\amp, 1).play(quant: 3);

m = Pbind(\instrument, \snare,
\delta, 1/16,
\amp, Pseq([Pseq([0, 0, 0, 0, 4, 0, 0, 0], 12),
Prand([0.15, 4, 0.125, 0, 0.5, 2], 32)],
inf),
\envlp, Prand([-16, -24, -32, -64], inf),
\phase, Prand([0.75pi, 0.5pi, 0.25pi, 0, pi, 0.75pi, 0.5pi, 0.25pi, 0,
0.75pi], inf)).play(quant:1);

p = Pbind(\instrument, \closedhat,
\delta, Pwrand([
Pseq([0.5, 0.0625, 0.0625 + rand(0.001, 0.002), 0.125, 0.25]), Pseq([0.5,
0.0625, 0.125,  0.0625]), Pseq([0.125, 0.125], inf)], [25, 25,
75].normalizeSum, inf)).play(quant: 1);

l = Pmono(\echo,
\delay, Pseq([0.125, 0.0625, 0.25], inf) * TempoClock.beatDur,
\decay, Pxrand([0.5, 1, 0.4, 0.3], inf) * 4,
\amp, Prand([1, 0.1, 0.3, 0.5, 0.8, 0, 0, 0], inf),
\dry, Prand([0.5,0], inf)
).play(quant: 1);

)

