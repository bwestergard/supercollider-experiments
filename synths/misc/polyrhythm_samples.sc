SynthDef(\playback, {arg buf, vol, t_trig;

var out;

out = PlayBuf.ar(2, bufnum: buf, trigger: t_trig);
out = out * XFade2.ar(Silence.ar, out, (vol * 2) - 1) * 2;

Out.ar(0, out);

}).send(s);

(
// read a whole sound into memory
s = Server.local;
// note: not *that* columbia, the first one
a = Buffer.read(s,"/home/bjorn/audio_work/samples/48027__smoseson__D3.wav"); // remember to free the buffer later.
)


x = Synth(\playback, [\buf, a.bufnum, \vol, 1]);
y = Synth(\playback, [\buf, b.bufnum, \vol, 1]);

r = Routine {
	var count,note,cycle,period,new_period,a,b,new_a,new_b,a_note,b_note,c_note;
	period = 1/2;
	cycle = 24;
	count = 0;
	a = 1;
	b = 1;
	a_note = 1;
	b_note = 2;
	period.postln;
	inf.do {
		if ((count % a) == 0, {
			if (a_note == 1, {
     				x.set(\t_trig, 1, \vol, 1 - (count / (a*b*cycle))); // Initially this "stream" of pulses is at full volume
			}, {
				y.set(\t_trig, 1, \vol, 1 - (count / (a*b*cycle)));
			});
		},{});
		if ((count % b) == 0, {
			if (a_note == 2, {
				x.set(\t_trig, 1, \vol, count / (a*b*cycle)); // Initially this "stream" of pulses is at full volume
			}, {
				y.set(\t_trig, 1, \vol, count / (a*b*cycle));
			});
		},{}); 
		period.wait;
		count = count + 1;
		if (count == ((a*b*cycle)), {
     			count = 0; // reset count
			if (period <= 0.05,
			{new_a = 1; new_b = 4; "going down, getting too fast".postln; },
			{ if (2.rand == 0, {new_a = 3; new_b = 4; "going down".postln; }, {new_a = 2; new_b = 1; "going up".postln; }); });
			if ((b * period) >= 1, { new_a = 3; new_b = 1; "too slow, speeding up!".postln; } );
			new_period = (b * period) / new_a; // calculate new period to ensure it matches old

			period = new_period;
			a = new_a;
			b = new_b;

			period.postln; // report new period

			c_note = a_note; // switch a and b notes
			a_note = b_note;
			b_note = c_note;
		},{});
	}
};

r.play;
r.stop;
