     SynthDef("ana_kick", {    
         arg outBus=0;  
         var env, env2, env3, pch, osc, noise, out;  
         env = EnvGen.kr(Env.perc(0, 0.20, 1, -4), doneAction:2);  
         env2 = EnvGen.kr(Env.new([60,3,0],[0.08,0.16],[-18,-5]));  
         env3 = EnvGen.kr(Env.new([0.8,0],[0.10],[-10]));  
         pch = (MouseX.kr(10,50)+env2).midicps;  
           
         osc = SinOsc.ar(pch, 0, env);  
         noise = BPF.ar(WhiteNoise.ar(env3), 200, 2);  
         out = osc+noise;  
         Out.ar(outBus, out.dup);  
     }).send(s);
     
     Synth(\ana_kick);
     
     ~dur = [2,[[3,[2,1]],3,[3,1!3],3,[4,1!6]]].convertRhythm;
     ~dur = [2,[3,3,3,[3,[2,1]],[4,[3,1]]]].convertRhythm;
     Pbind(\instrument, \ana_kick,
           \dur, Pseq([Plazy{ Pseq(~dur) },1/(Pstep([20-12,25,27],1/3).midicps)],inf)
           ).play;
           
SynthDef(\mousefilt,{
var input = In.ar(0, 2);
input = RLPF.ar(input, LFNoise2.kr(12).range(20,2000) * LFNoise0.kr(8).range(1,3).round(1), 0.3);
ReplaceOut.ar(0, input);
}).store;

~filt = Synth(\mousefilt);
~filt.free;
           
           entity framework
           Nhibernate
           
           Help.gui
