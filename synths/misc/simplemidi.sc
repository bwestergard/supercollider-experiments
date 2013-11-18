
(

MIDIIn.connect;

s = Server.local;

s.boot;

s.latency = 0;


SynthDef("sik-goo", { arg freq=440,formfreq=100,gate=0.0,bwfreq=800;

var x;

x = Formant.ar(

SinOsc.kr(0.02, 0, 10, freq), 

formfreq,

bwfreq

);

x = EnvGen.kr(Env.adsr, gate,Latch.kr(gate,gate)) * x;

Out.ar(0, x);

}).send(s);


x = Synth("sik-goo");


//set the action:

MIDIIn.noteOn = {arg src, chan, num, vel;

x.set(\freq, num.midicps / 4.0);

x.set(\gate, vel / 200 );

x.set(\formfreq, vel / 127 * 1000);

};

MIDIIn.noteOff = { arg src,chan,num,vel;

x.set(\gate, 0.0);

};

MIDIIn.bend = { arg src,chan,val;

//(val * 0.048828125).postln;

x.set(\bwfreq, val * 0.048828125 );

};

)
