SynthDef(\zap, {arg freq, vol, period;

var out, carrier, voices, width;

out = SinOsc.ar(freq).dup;
out = out * Line.ar(1,0,period, doneAction: 2) * Line.ar(0,1,0.01) * AmpComp.kr(freq, (70-24).midicps) * 0.5 * XFade2.ar(Silence.ar, out, (vol * 2) - 1);


Out.ar(0, out);

}).send(s);

Synth(\zap, [\freq, 440, \vol, 1, \gate, 1, \period, 3]);

r = Routine {
	var count,note,cycle,period,new_period;
	period = new_period = 1/8;
	cycle = 64;
	count = 0;
	note = 40.midicps;
	inf.do {
		new_period.postln;
		("count is " + count).postln;
		if ((count % 2) == 0, {
     			Synth(\zap, [\freq, note, \vol, 1, \period, 3*period/2]); // Initially this "stream" of pulses is at full volume
		},
		{
			Synth(\zap, [\freq, note, \vol, 1 - (count / cycle), \period, period]); // Starts silent, fades to full
		});
		if (count == cycle, {count = 0; new_period = period; });
		new_period = (period * 2) - (period * (count/cycle));
		new_period.wait;
		count = count + 1;
	}
};

r.play;
r.stop;

(2 % 2).postln;
