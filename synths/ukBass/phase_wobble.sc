b = Buffer.alloc(s,44100,1);


{
	var gate,notes,note,wobbles,wobble;
	gate = Impulse.kr(1/2);
	notes = Dseq([0, 3, 7, 12], inf);
	wobbles = Drand([2, 1/2, 3/2], inf);
	note = Demand.kr(gate, 0, notes) + 37;
	wobble = Demand.kr(gate, 0, wobbles);
	z = VarSaw.ar(note.midicps, 0, LFNoise2.ar(1/2).range(0.15,0.1));
	z = z + BufAllpassC.ar(b.bufnum, z, LFTri.ar(wobble, iphase: pi/2).range(0.01+0.002, 0.01), 0.1).dup * 0.05;
	z = BLowPass.ar(z, Slew.kr(note,36,36).midicps, 0.8);
	Out.ar(0, z.dup);
}.play;
