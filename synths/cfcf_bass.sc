SynthDef(\cfcf,{|freq = 40,amp|
var saw,blok,tone,gate,freq_env,res_env,note,dur;
dur = 2;
note = freq.cpsmidi + LFNoise2.ar(12).range(1/12,-1/12);
gate = Impulse.ar(0);
freq_env = EnvGen.ar(Env.new([note.midicps, 2000, 20], [3*dur/8, 3*dur/8]/2,'exponential'),gate);
res_env = XLine.ar(0.1,0.9,dur/2);
saw = VarSaw.ar(note.midicps, width: 0.4, mul: 0.5) + VarSaw.ar(note.midicps*4, width: 0.4, mul: 0.4);
blok = Pulse.ar(note.midicps, width: 0.4);
tone = XFade2.ar(saw,blok,0) * 0.2;
tone = MoogLadder.ar(tone, XLine.ar(4000,100,dur/8), res_env) * 4;
tone = tone + SinOsc.ar(note.midicps, mul: 0.5);
tone = Compander.ar(tone, tone, thresh: 1/4, slopeAbove:1/40, clampTime: 1/80) * 4;
tone = tone * XLine.ar(1,1/1000,dur, doneAction:2) * 0.5;
Out.ar(0,tone.dup * amp);
}).store;




