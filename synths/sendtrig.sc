
s = Server.local;
s.boot;

SynthDef("help-SendTrig",{
	var sig,thresh,trig,amp,freq,hasFreq,delay;
	delay = 0.2;
	thresh = MouseX.kr(0,1);
	sig = AudioIn.ar([1,2]).sum;
	amp = Amplitude.kr(sig);
	trig = Schmidt.kr(amp,thresh,thresh);
	#freq, hasFreq = Tartini.kr(sig);
	SendTrig.kr(DelayN.kr(trig,delay,delay),0,freq);
	Out.ar(0, sig.dup);
}).send(s);

SynthDef(\ping, {|freq = 50|
var tone = SinOsc.ar(freq * [1,1.001]);
tone = tone * XLine.kr(1/2,1/1000,3,doneAction:2);
Out.ar(0,tone);
}).store;

// register to receive this message
OSCresponder(s.addr,'/tr',{ arg time,responder,msg;
        [time,responder,msg].postln;
        Synth(\ping, [\freq, msg[3]]);
}).add;

Synth("help-SendTrig");

