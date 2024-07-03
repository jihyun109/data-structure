package my;

import java.util.Arrays;
import java.util.Scanner;

public class AverageSort {

	public static int[] aveSort(int[] x, double ave) {
		int d = x.length / 3;

		int[] a = new int[d];
		int[] b = new int[d];
		int[] c = new int[x.length - (2 * d)];
		int[] srtd = new int[x.length];

		if (x.length > 2)
			splite(x, a, b, c);

		if (x.length == 2)
			splite(x, a, b);

		if (a.length > 1)
			a = aveSort(a, ave);
		if (b.length > 1)
			b = aveSort(b, ave);
		if (c.length > 1)
			c = aveSort(c, ave);

		if (x.length > 2)
			srtd = sort(ave, x, a, b, c);
		else if (x.length == 2)
			srtd = sort(ave, x, a, b);

		return srtd;
	}

	public static int[] sort(double ave, int[] x, int[] a, int[] b) {
		int[] srtd = new int[x.length];
		int apnt = 0;
		int bpnt = 0;
		int spnt = 0;
		
		while (apnt < a.length && bpnt < b.length)
			if (Math.min(diff(ave, a[apnt]), diff(ave, b[bpnt])) == diff(ave, a[apnt]))
				srtd[spnt++] = a[apnt++];
			else if (Math.min(diff(ave, a[apnt]), diff(ave, b[bpnt])) == diff(ave, b[bpnt]))
				srtd[spnt++] = b[bpnt++];
		
		if (apnt == a.length)
			for(int i = bpnt; i < b.length; i++)
				srtd[spnt++] = b[bpnt++];
		else if (bpnt == b.length)
			for(int i = apnt; i < a.length; i++)
				srtd[spnt++] = a[apnt++];
		
		return srtd;
	}

	public static void splite(int[] x, int[] a, int[] b) {
		int idx = 0;

		for (int i = 0; i < a.length; i++)
			a[i] = x[idx++];
		for (int i = 0; i < b.length; i++)
			b[i] = x[idx++];
	}

	public static double ave(int[] x) {
		int sum = 0;

		for (int i = 0; i < x.length; i++) {
			sum += x[i];
		}
		double ave = (double) sum / x.length;

		return ave;
	}

	public static int[] sort(double ave, int[] x, int[] a, int[] b, int[] c) {
		int[] srtd = new int[x.length];
		int apnt = 0;
		int bpnt = 0;
		int cpnt = 0;
		int spnt = 0;

		while (apnt < a.length && bpnt < b.length && cpnt < c.length)
			if (min(diff(ave, a[apnt]), diff(ave, b[bpnt]), diff(ave, c[cpnt])) == diff(ave, a[apnt]))
				srtd[spnt++] = a[apnt++];
			else if (min(diff(ave, a[apnt]), diff(ave, b[bpnt]), diff(ave, c[cpnt])) == diff(ave, b[bpnt]))
				srtd[spnt++] = b[bpnt++];
			else if (min(diff(ave, a[apnt]), diff(ave, b[bpnt]), diff(ave, c[cpnt])) == diff(ave, c[cpnt]))
				srtd[spnt++] = c[cpnt++];

		if (apnt == a.length) {
			while (bpnt < b.length && cpnt < c.length)
				if (Math.min(diff(ave, b[bpnt]), diff(ave, c[cpnt])) == diff(ave, b[bpnt]))
					srtd[spnt++] = b[bpnt++];
				else if (Math.min(diff(ave, b[bpnt]), diff(ave, c[cpnt])) == diff(ave, c[cpnt]))
					srtd[spnt++] = c[cpnt++];

			if (bpnt == b.length) {
				while (cpnt < c.length)
					srtd[spnt++] = c[cpnt++];
			} else if (cpnt == c.length) {
				while (bpnt < b.length)
					srtd[spnt++] = b[bpnt++];
			}

		} else if (bpnt == b.length) {
			while (apnt < a.length && cpnt < c.length)
				if (Math.min(diff(ave, a[apnt]), diff(ave, c[cpnt])) == diff(ave, a[apnt]))
					srtd[spnt++] = a[apnt++];
				else if (Math.min(diff(ave, a[apnt]), diff(ave, c[cpnt])) == diff(ave, c[cpnt]))
					srtd[spnt++] = c[cpnt++];

			if (apnt == a.length) {
				while (cpnt < c.length)
					srtd[spnt++] = c[cpnt++];
			} else if (cpnt == c.length) {
				while (apnt < a.length)
					srtd[spnt++] = a[apnt++];
			}

		} else if (cpnt == c.length) {
			while (apnt < a.length && bpnt < b.length)
				if (Math.min(diff(ave, a[apnt]), diff(ave, b[bpnt])) == diff(ave, a[apnt]))
					srtd[spnt++] = a[apnt++];
				else if (Math.min(diff(ave, a[apnt]), diff(ave, b[bpnt])) == diff(ave, b[bpnt]))
					srtd[spnt++] = b[bpnt++];

			if (apnt == a.length) {
				while (bpnt < b.length)
					srtd[spnt++] = b[bpnt++];
			} else if (bpnt == b.length) {
				while (apnt < a.length)
					srtd[spnt++] = a[apnt++];
			}

		}
		return srtd;
	}

	public static double min(double d, double e, double f) {
		double minAB = Math.min(d, e);
		double min = Math.min(minAB, f);
		return min;
	}

	public static double diff(double ave, int a) {
		double diff = Math.abs(ave - a);
		return diff;
	}

	public static void splite(int[] x, int[] a, int[] b, int[] c) {
		int idx = 0;

		for (int i = 0; i < a.length; i++)
			a[i] = x[idx++];
		for (int i = 0; i < b.length; i++)
			b[i] = x[idx++];
		for (int i = 0; i < c.length; i++)
			c[i] = x[idx++];
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		System.out.print("요솟수: ");
		int n = s.nextInt();
		int x[] = new int[n];

		for (int i = 0; i < n; i++) {
			System.out.print("x[" + i + "]: ");
			x[i] = s.nextInt();
		}

		System.out.println("평균값에 가까운 순서로 정렬합니다.");
		double ave = ave(x);
		x = aveSort(x, ave);

		System.out.println("x[]: " + Arrays.toString(x));
	}

}