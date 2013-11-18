z = {
	var note = 34+0;
	var dur = 0.8;
	BMoog.ar(
		LFTri.ar(XLine.ar(note.midicps/4,note.midicps,dur*0.065)),
		SinOsc.ar(3).range(1000, note.midicps * 1.5), // cutoff freq.
		0.8, // q
		0, // mode - lowpass
		2).dup * 2 // mul
}.play
