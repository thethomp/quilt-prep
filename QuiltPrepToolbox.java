


public class QuiltPrepToolbox {


	public QuiltPrepToolbox() {

	}

	public double parseString( String s ) {
	  try {
		if( s.contains(" ") ) {
			String[] split = s.split(" "); // split by blank spaces
			if( split.length == 1 ) {
				return (double)(Integer.parseInt(split[0]));
			}
			else if( split.length == 2 ) {
				int firstNum = Integer.parseInt(split[0]);
				String[] secondSplit = split[1].split("/");
				return (double)(firstNum + ((double)Integer.parseInt(secondSplit[0]))/((double)Integer.parseInt(secondSplit[1])));
			}
			else
				return 0;
		}
		else
			return Double.parseDouble(s);
	  } 
	  catch ( NumberFormatException nfe ) {
			return -1;
	  }

	}

	public double computeTotal( char type, double totalWidth, double width, 
								double height, double qty ) {
		// types:
		// q = square, s = rectangle
		switch (type) {
		case 'q':
			double q_temp = Math.floor( totalWidth/width );
			return Math.ceil(qty/q_temp) * width;			
		case 's':
		/*	if( Math.max(width,height) < totalWidth )
				return qty*(Math.min(width,height));
			else
				return Math.ceil((qty*Math.min(width,height))/totalWidth); 
		*/
			double s_temp = Math.floor( totalWidth/Math.max(width,height) );
			return Math.ceil(qty/s_temp) * Math.min(width,height);
		}
		return -1;

	}




}
