/**
 * This class implements arithmetic and boolean operators on
 * higher precision decimal numbers than are available with the
 * primitive double and long types.
 * 
 * 
 */

public class RealNumber {
	/**
	 * Number of digits after decimal
	 */
	private int precision;

	/**
	 * Flag indicating whether the number is negative
	 */
	private boolean isNegative;

	/**
	 * Storage for the data for the decimal number
	 */
	private byte[] data;

	/**
	 * Constructor to initialize when data is provided in byte form in the same
	 * order as expected by this class, low order digits of decimal first
	 * 
	 * @param precision
	 *            Number of digits after decimal
	 * @param isNegative
	 *            Is number is negative
	 * @param data
	 *            Contents for this decimal number
	 */
	public RealNumber(int precision, boolean isNegative, byte[] data) {

		int length = data.length; // length of data to copy

		// Silently discard bad precision
		if (precision < 0)
			precision = 0;

		if (precision > data.length) {
			// Since we have only digits after decimal, so worst case will be
			// precision
			this.data = new byte[precision];
		}
		else {
			int i;

			for (i = length - 1; (i > precision - 1) && (data[i] == 0); i--)
				;
			this.data = new byte[i + 1];
			length = this.data.length;
		}
		this.precision = precision;
		this.isNegative = isNegative;

		// Fill zeros and initialize array
		fillArray(this.data, 0, (byte) 0);

		for (int i = 0; i < length; ++i) {
			this.data[i] = data[i];
		}
	}

	/**
	 * Constructor to initialize when decimal data is provided by a string in
	 * natural order, for example 3.432
	 * 
	 * @param precision
	 *            Number of digits after decimal
	 * @param value
	 *            decimal number in string format
	 */
	public RealNumber(int precision, String value) {

		// Silently discard bad precision
		if (precision < 0)
			precision = 0;

		this.precision = precision;

		if (value.length() == 0) {
			// Throw exception
			throw new NumberFormatException();
		}

		int startValueIndex = 0; // start of valid value index, when negative
									// sign this will be incremented to 1

		// check for negative number
		if (value.charAt(startValueIndex) == '-') {
			this.isNegative = true;
			startValueIndex++;
		}

		int i;
		boolean decimalChecked = false; // To avoid more than one decimal point
		for (i = startValueIndex; i < value.length(); ++i) {
			if (value.charAt(i) == '.') {
				if (decimalChecked)
					throw new NumberFormatException(); // 1 + decimals
				decimalChecked = true;
			}
			else if ((value.charAt(i) != '.')
					&& !Character.isDigit(value.charAt(i))) {
				// throw exception
				throw new NumberFormatException();
			}
		}

		int length = value.length(); // Store length of data to copy
		int index = 0; // Index into our data from where we will copy the bytes
						// from the string value
		int allocateLength = length - startValueIndex; // length of byte array
														// to allocate

		boolean round = false; // set when we need to round
		// First we fill data with the 0s if the precision is more or reduce
		// length if less
		// Locate decimal point
		int decimalIndex = value.indexOf('.');
		int valuePrecision = 0;
		if (decimalIndex >= 0) {
			valuePrecision = length - 1 - decimalIndex;
			// If the precision is more than the desired precision we can reduce
			// length
			if (valuePrecision > precision) {
				length -= (valuePrecision - precision);
				allocateLength -= (valuePrecision - precision);
				if ((value.charAt(length) - '0') >= 5) {
					round = true;
				}
			}
			else if (valuePrecision < precision) {
				index = precision - valuePrecision;
			}
			allocateLength--; // compensate for decimal
		}
		else {
			index = precision;
		}

		// Allocate enough to hold the length + index.
		// index will be more than 0 when the precision is more than what is
		// provided in the string
		// this.data = new byte[index + length + 1];
		this.data = new byte[index + allocateLength];
		fillArray(this.data, 0, (byte) 0);

		int dataIndex = index; // Index from where to copy
		// Now we will fill the data from the length to the index
		for (i = length - 1; i >= startValueIndex; --i) {
			if (value.charAt(i) != '.') {
				this.data[dataIndex++] = (byte) ((byte) value.charAt(i) - '0');
			}
		}

		if (round) {
			// When we have to round we need to propagate upwards
			// like 1.296 with precision 2 round will be 1.3
			boolean carry = false; // Do we need to account for carry
			if ((this.data[0] + 1) >= 10) {
				carry = true;
				this.data[0] = 0;
				// For the range assign to data and add carry
				for (i = 1; i < this.data.length; ++i) {
					byte val = this.data[i];
					if (carry) {
						val++;
						if (val >= 10)
							this.data[i] = 0;
						else {
							carry = false;
							this.data[i] = val;
						}
					}
				}
			}
			else {
				this.data[0]++;
			}
		}

	}

	/**
	 * A get method to get precision
	 */
	public int getPrecision() {
		return precision;
	}

	/**
	 * A get method to get negative flag
	 */
	public boolean isNegative() {
		return isNegative;
	}

	/**
	 * A get method to get data in byte format
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * A helper method to initialize byte array
	 * 
	 * @param array
	 *            Byte array to fill
	 * @param index
	 *            Index from where to start
	 * @param value
	 *            value to use for filling
	 */
	public static void fillArray(byte[] array, int index, byte value) {
		int i;

		for (i = index; i < array.length; ++i) {
			array[i] = value;
		}
	}

	/**
	 * This method converts to string representation of data
	 * 
	 * @return A formatted string of data in this object
	 * 
	 */
	public String toString() {
		byte[] localData = getData(); // A local reference to the byte data
		int length = localData.length; // Length of data in this object
		int precision = getPrecision(); // Precision for this object

		StringBuilder store = new StringBuilder(); // A storage to make the
													// string

		if (isNegative()) {
			store.append('-');
		}

		if (length == 0)
			return null;

		int i;

		/*
		 * We will start from the highest index in the data[]. This way we can
		 * get read of leading zeros and also push the most significant digit
		 * first.
		 */

		/*
		 * skip leading 0s before any non zero value before decimal. 3.14159 can
		 * be expressed {9,5,1,4,1,3} with precision 5 if there are more values
		 * in this precision for example {9,5,1,4,1,3,0,0,0,0}, then 4 zero from
		 * right can be ignored as this value will be 00003.14159, where leading
		 * zeros can be ignored
		 */
		for (i = length - 1; (i > precision - 1) && (localData[i] == 0); i--)
			;

		if (i < 0)
			return null;

		for (; i >= 0; i--) {
			/*
			 * We need to check +1 as the data index start from 0
			 */
			if (i + 1 == precision)
				store.append('.');

			// Add 48 to digit to convert to ASCII
			store.append((char) ('0' + localData[i]));
		}

		return store.toString();
	}

	/**
	 * This is a helper method to compare two RealNumbers ignoring sign
	 * (absolute value). We will use this method in compare(), add() and sub()
	 * methods, those function will deal with treatment of signs.
	 * 
	 * @param value1
	 *            The first Real number
	 * @param value2
	 *            The second Real number to compare with value1
	 * @return 1 if value1 > value2, -1 if value1 < value2 or 0 if value1 is
	 *         equal to value2
	 * 
	 */
	public static int compareAbsolute(RealNumber value1, RealNumber value2) {

		byte[] localData1 = value1.getData(); // Reference to data for value1
		byte[] localData2 = value2.getData(); // Reference to data for value2

		/*
		 * We have two parts, one pre decimal and one post decimal we will
		 * compare the pre decimal byte by byte and, if they are equal then we
		 * will go post decimal byte by byte. In this order, any larger value
		 * found we have result. For example if we compare 51.23 and 42.39, the
		 * moment we compare 5 and 4, we are done. Sometime we can have
		 * different number of digits pre decimal or post decimal In this case
		 * we can have for example we can have 05.33 and 2.20. So we either
		 * remove leading 0, before 5 and compare or follow a common order,
		 * assuming 0 is in front of 2 so 05.33 and 02.20 (We will not insert 0
		 * in front of two, but simply skip.
		 */

		// Pre decimal comparison start...
		// Lengths pre decimal for the values
		int lengthPreDecimal1 = localData1.length - value1.getPrecision();
		int lengthPreDecimal2 = localData2.length - value2.getPrecision();

		// Max of the two pre-decimal length
		int maxPreDecimal = lengthPreDecimal1;

		if (lengthPreDecimal1 > lengthPreDecimal2) {
			maxPreDecimal = lengthPreDecimal1;
		}
		else
			maxPreDecimal = lengthPreDecimal2;

		/*
		 * - Simple case 31.52 2.51 represented with prec 2 {2 5 1 3} and {1 5
		 * 2} lengthPreDecimal1 = 2, lengthPreDecimal2 = 1
		 */
		int i, j, k;
		boolean done = false; // flag to exit loop
		int returnValue = 0; // return value for this method

		for (i = maxPreDecimal - 1; i >= 0 && !done; --i) {
			byte cmp = 0; // used to compare by subtracting the values present
			if (i < lengthPreDecimal1) {
				cmp = localData1[i + value1.getPrecision()];
			}
			if (i < lengthPreDecimal2) {
				cmp -= localData2[i + value2.getPrecision()];
			}
			if (cmp > 0) {
				done = true;
				returnValue = 1;
			}
			else if (cmp < 0) {
				done = true;
				returnValue = -1;
			}
			// if compare 0 then we need to continue to next lower digit
			// for example if 56.3 and 52.3, after comparing the first digits 5,
			// 5,
			// we need to continue to compare 6 and 2.
		}
		if (done) {
			// We already have result comparing pre decimal
			return returnValue;
		}

		/*
		 * We will follow similar logic above for post decimal, but there is
		 * difference now top index needs to be adjusted. for example 3.1234 and
		 * 3.13, which are stored as {4 3 2 1 3} precision 4 and {3 1 3}
		 * precision 2 Here the locatData1 can be from 0 to 4 but localData2
		 * will be 0 to 2. But when we compare we have to start from each
		 * precision and compare in this case 1 from value 1 with 1 from value
		 * 2, 2 from value 1 with 3 from value 2 and digit 3 and 4 from value1
		 * has no digits in value 2
		 */
		int lengthPostDecimal1 = value1.getPrecision();
		int lengthPostDecimal2 = value2.getPrecision();

		int maxpostDecimal; // max post decimal digit count
		if (lengthPostDecimal1 > lengthPostDecimal2) {
			maxpostDecimal = lengthPostDecimal1;
		}
		else {
			maxpostDecimal = lengthPostDecimal2;
		}

		i = value1.getPrecision() - 1;
		j = value2.getPrecision() - 1;
		for (k = maxpostDecimal - 1; k >= 0 && !done; --k) {
			int val1 = 0;
			int val2 = 0;

			if (i >= 0)
				val1 = localData1[i];

			if (j >= 0)
				val2 = localData2[j];

			if ((val1 - val2) > 0) {
				returnValue = 1;
				done = true;
			}
			else if ((val1 - val2) < 0) {
				returnValue = -1;
				done = true;
			}
			// Else loop
			--i;
			--j;
		}

		return returnValue;
	}

	/**
	 * This is a method to compare two RealNumbers taking care of number sign
	 * 
	 * @param value1
	 *            The first Real number
	 * @param value2
	 *            The second Real number to compare with value1
	 * @return 1 if value1 > value2, -1 if value1 < value2 or 0 if value1 is
	 *         equal to value2
	 * 
	 */
	public static int compare(RealNumber value1, RealNumber value2) {
		// First check the signs, if they are different then no need to compare
		// each digit
		boolean inverseResult = false; // when compare two negative numbers,
										// this will be true
		if (!value1.isNegative() && value2.isNegative()) {
			return 1;
		}
		else if (value1.isNegative() && !value2.isNegative()) {
			return -1;
		}
		else if (value1.isNegative() && value2.isNegative()) {
			inverseResult = true;
		}

		int returnValue = compareAbsolute(value1, value2);
		if (inverseResult) {
			if (returnValue == 1)
				return -1;
			else if (returnValue == -1)
				return 1;
		}

		return returnValue;
	}

	/**
	 * This is a method to compare() method but compares the current RealNumber
	 * object with another RealNumber object passed as parameter.
	 * 
	 * @param value1
	 *            The Real number to compare
	 * @return 1 if value1 > value2, -1 if value1 < value2 or 0 if value1 is
	 *         equal to value2
	 * 
	 */
	public int compareTo(RealNumber value) {
		return compare(this, value);
	}

	/**
	 * This is a method overrides the equal method of Object class.
	 * 
	 * @param object
	 *            A RealNumberObject
	 * @return 1 True if objects are numerically equal, else False.
	 * 
	 */
	public boolean equals(Object o) {
		if (o instanceof RealNumber) {
			int ret = compareTo((RealNumber) o);

			if (ret == 0)
				return true;
			else
				return false;
		}
		else
			return false;
	}

	/**
	 * This is a helper method to add two Real Numbers. This is used by add()
	 * and sub() methods to add.
	 * 
	 * @param value1
	 *            The first Real number
	 * @param value2
	 *            The second Real number
	 * @param negative
	 *            If true a negative Real number is returned
	 * @return RealNumber Returns a a real number adding two real numbers with
	 *         the larger of precision of two numbers and sign passed as
	 *         parameter
	 * 
	 */
	public static RealNumber addOp(RealNumber value1, RealNumber value2,
			boolean negative) {
		byte[] localData1 = value1.getData(); // Local reference to data of
												// value1
		byte[] localData2 = value2.getData(); // Local reference to data of
												// value2

		int precision; // Resultant precision (larger of value1 and value 2
		int value1StartIndex = 0; // index where the actual number starts for
									// value1
		int value2StartIndex = 0; // index where the actual number starts for
									// value1

		/*
		 * We need to take care of precision, we take the larger one, but we
		 * should be able to skip the digits in smaller number. For example if
		 * we add 0.1234 and 0.12, When we skip the fist two digits of number 2
		 * and assume 0. So Value1StartIndex will be 0 and Value2StartIndex will
		 * be 2
		 */
		if (value1.getPrecision() > value2.getPrecision()) {
			precision = value1.getPrecision();
			value1StartIndex = 0;
			value2StartIndex = value1.getPrecision() - value2.getPrecision();
		}
		else {
			precision = value2.getPrecision();
			value2StartIndex = 0;
			value1StartIndex = value2.getPrecision() - value1.getPrecision();
		}

		int length; // Length of resultant data
		/*
		 * We compare before decimal which is larger to account of larger number
		 * This we will add the precision calculated in previous step to get
		 * length
		 */
		if ((localData1.length - value1.getPrecision()) > localData2.length
				- value2.getPrecision())
			length = localData1.length - value1.getPrecision();
		else
			length = localData2.length - value2.getPrecision();

		byte[] resultData = new byte[precision + length]; // Storage for result
		fillArray(resultData, 0, (byte) 0); // Fill storage with 0s

		int i;
		boolean carry = false; // if there is carry in previous digit add
		for (i = 0; i < resultData.length; ++i) {
			byte sum = 0;

			/*
			 * While adding we will skip the index where there are no contents
			 * and assume 0, For example when we do 0.345 + 0.12, we can use
			 * just 5 as the sum of the very first digit by skipping reading
			 * from value2 and assume 0
			 */
			if (i >= value1StartIndex
					&& i < (localData1.length + value1StartIndex))
				sum += localData1[i - value1StartIndex];

			if (i >= value2StartIndex
					&& i < (localData2.length + value2StartIndex))
				sum += localData2[i - value2StartIndex];

			if (carry)
				sum++;

			// We need to propagate carry up
			if (sum > 9) {
				carry = true;
				sum = (byte) (sum - 10);
			}
			else
				carry = false;

			resultData[i] = sum;
		}

		return new RealNumber(precision, negative, resultData);
	}

	/**
	 * This is a helper method to subtract two Real Numbers. This is used by
	 * add() and sub() methods to add.
	 * 
	 * @param value1
	 *            The first Real number
	 * @param value2
	 *            The second Real number
	 * @param negative
	 *            If true a negative Real number is returned
	 * @return RealNumber Returns a a real number subtracting two real numbers
	 *         with the larger of precision of two numbers and sign passed as
	 *         parameter
	 * 
	 */
	public static RealNumber subOp(RealNumber value1, RealNumber value2,
			boolean negative) {
		byte[] localData1 = value1.getData(); // Local reference to data of
												// value1
		byte[] localData2 = value2.getData(); // Local reference to data of
												// value 2

		int precision; // Resultant precision
		int value1StartIndex = 0; // start of valid index for value1
		int value2StartIndex = 0; // start of valid index for value2

		/*
		 * The explanation of the logic below is same as addOp explanation
		 * except we will subtract later
		 */
		if (value1.getPrecision() > value2.getPrecision()) {
			precision = value1.getPrecision();
			value1StartIndex = 0;
			value2StartIndex = value1.getPrecision() - value2.getPrecision();
		}
		else {
			precision = value2.getPrecision();
			value2StartIndex = 0;
			value1StartIndex = value2.getPrecision() - value1.getPrecision();
		}

		int length;
		if ((localData1.length - value1.getPrecision()) > localData2.length
				- value2.getPrecision())
			length = localData1.length - value1.getPrecision();
		else
			length = localData2.length - value2.getPrecision();

		byte[] resultData = new byte[precision + length];
		fillArray(resultData, 0, (byte) 0);

		int i;
		boolean borrow = false; // Whether we borrowed to subtract
		for (i = 0; i < resultData.length; ++i) {
			byte sub = 0;

			if (borrow)
				sub = -1;

			// Only subtract when a valid digit is present at the index
			if (i >= value1StartIndex
					&& i < (localData1.length + value1StartIndex))
				sub += localData1[i - value1StartIndex];

			if (i >= value2StartIndex
					&& i < (localData2.length + value2StartIndex))
				sub -= localData2[i - value2StartIndex];

			if (sub < 0) {
				borrow = true;
				sub = (byte) ((byte) 10 - Math.abs((int) sub));
			}
			else
				borrow = false;

			resultData[i] = sub;
		}

		return new RealNumber(precision, negative, resultData);
	}

	/**
	 * This is a method to add two Real Numbers. Based on sign this may result
	 * in subtract operation. Correct sign is added to the resultant Real
	 * Number.
	 * 
	 * @param value1
	 *            The first Real number
	 * @param value2
	 *            The second Real number
	 * @return RealNumber Returns a a real number adding two real numbers with
	 *         the larger of precision of two numbers
	 * 
	 */
	public static RealNumber add(RealNumber value1, RealNumber value2) {
		RealNumber res = null; // Real number returned

		if (!value1.isNegative() && !value2.isNegative()) {
			// Both number are positive
			res = addOp(value1, value2, false);
		}
		else if (value1.isNegative() && value2.isNegative()) {
			// Both numbers are negative
			res = addOp(value1, value2, true);
		}
		else {
			boolean negative = false; // to determine sign of the result

			// Compare the number to add correct sign
			int returnVal = compareAbsolute(value1, value2);
			if (returnVal < 0)
				negative = true;

			if (!value1.isNegative() && value2.isNegative()) {
				// Value 1 positive and value2 is negative
				if (negative)
					res = subOp(value2, value1, true);
				else
					res = subOp(value1, value2, false);
			}
			else if (value1.isNegative() && !value2.isNegative()) {
				if (negative)
					res = subOp(value2, value1, false);
				else
					res = subOp(value1, value2, true);

			}
		}

		return res;
	}

	/**
	 * This is a method to subtract two Real Numbers. Based on sign this may
	 * result in add operation. Correct sign is added to the resultant Real
	 * Number.
	 * 
	 * @param value1
	 *            The first Real number
	 * @param value2
	 *            The second Real number
	 * @return RealNumber Returns a a real number subtracting two real numbers
	 *         with the larger of precision of two numbers
	 * 
	 */
	public static RealNumber subtract(RealNumber value1, RealNumber value2) {
		RealNumber res = null; // to return results

		if (!value1.isNegative() && value2.isNegative()) {
			res = addOp(value1, value2, false);
		}
		else if (value1.isNegative() && !value2.isNegative()) {
			res = addOp(value1, value2, true);
		}
		else {
			boolean negative = false; // to determine sign of the result

			int returnVal = compareAbsolute(value1, value2);
			if (returnVal < 0) {
				negative = true;
			}

			if (!value1.isNegative() && !value2.isNegative()) {
				if (negative)
					res = subOp(value2, value1, true);
				else
					res = subOp(value1, value2, false);
			}
			else if (value1.isNegative() && value2.isNegative()) {
				if (negative)
					res = subOp(value2, value1, false);
				else
					res = subOp(value1, value2, true);
			}
		}

		return res;
	}

	/**
	 * This is a helper method to multiplication. This will multiply one row
	 * with a number and return results
	 * 
	 * @param data
	 *            byte array for a row
	 * @param multiplier
	 *            A number to multiply
	 * @param multStore
	 *            A placeholder to store result
	 */
	public static void mulRow(byte[] data, byte muliplier, byte[] mulStore) {
		int i, j;

		for (i = 0; i < data.length; ++i) {
			mulStore[i] = (byte) (data[i] * muliplier);
		}
	}

	/**
	 * This is a helper method to multiplication and division. This round the
	 * result to a precision
	 * 
	 * @param byteStore
	 *            byte array to round
	 * @param length
	 *            length of above byte array to consider
	 * @param currentPrecision
	 *            current precision
	 * @param desiredPrecision
	 *            desired precision to round
	 */
	public static void round(byte[] byteStore, int length,
			int currentPrecision, int desiredPrecision) {

		// If the desired precision is 0 or less simply return
		if (desiredPrecision <= 0)
			return;

		// A flag to signal rounding operation is started
		boolean roundStart = false;
		// A flag to to propagate carry
		boolean carryFlag = false;

		// Index to start rounding
		int startByteIndex = currentPrecision - desiredPrecision;

		if (startByteIndex <= 0)
			return;

		int i;

		/*
		 * We start basically shift array members. For example we have a byte
		 * array with data {8, 7, 3, 4, 5, 6} with current precision of 4, so we
		 * have a decimal after after 4 in the array. If the desired precision
		 * is 2 then we need decimal after 2 in the array. So we basically shift
		 * by 2. so resultant data store will be {3, 4, 5, 6} without any
		 * rounding and with rounding {4, 4, 5, 6} because the second element
		 * "7" in the passed array which caused rounding
		 */
		for (i = 0; i < length; ++i) {
			if (i + startByteIndex < length) {
				if (!roundStart && (i + startByteIndex) > 0) {
					if (byteStore[i + startByteIndex - 1] >= 5)
						carryFlag = true;
					roundStart = true;
				}
				byte val = byteStore[i + startByteIndex];
				if (carryFlag) {
					val++;
					if (val >= 10)
						val = 0;
					else
						carryFlag = false;
				}
				byteStore[i] = val;
			}
			else {
				byteStore[i] = 0;
			}
		}
		return;
	}

	/**
	 * This is a method for multiplication for value 1 and value 2
	 * 
	 * @param value1
	 *            First Real Number 1
	 * @param value1
	 *            First Real Number 2
	 * @return Resultant real number with precision of larger number
	 */
	public static RealNumber multiply(RealNumber value1, RealNumber value2) {

		/*
		 * Example to multiply with two 2D array representation
		 * Multiply 123 x 232
		 *  Multiply Each row 0  with here 2 * 123 for row 0
		 *   x[0][0] = 6
		 *   x[0][1] = 4
		 *   x[0][2] = 2
		 *   Multiply row 1 with 3 3 * 123 for row 0
		 *   x[1][0] = 9
		 *   x[1][1] = 6
		 *   x[1][2] = 3
		 *   Multiply row 2 with 2 3 * 123 for row 0
		 *   x[2][0] = 6
		 *   x[2][1] = 4
		 *   x[2][2] = 2
		 *    
		 *    Manual steps how we add the 2D array numbers
		 *    		123
		 *    		232
		 *    ===========================
		 *    		246
		 *    	   369
		 *    	  246
		 *    =========
		 *         28536 
		 *         
		 *         We will add as we do manually
		 *         So to implement we need two loops [outer for result and inner for localData 1.
		 *         We will store the resultant addition in single dimension array such as 
		 *         result[i+j] += 2Darray[i][j] when the  is a valid number in localData
		 *         
		 *         0     x[0][0]
		 *         1     x[0][1] + x[1][0]
		 *         2     x[0][2] + x[1][1] + x[2][0]
		 *         3             + x[1][2] + x[2][1]
		 *         4                                 + x[2][2]
		 */
		
		byte[] localData1; // Reference to data in value1
		byte[] localData2; // Reference to data in value1

		// We use the larger length as the first as this used for two
		// dimensional array
		if (value1.getData().length > value2.getData().length) {
			localData1 = value1.getData();
			localData2 = value2.getData();
		}
		else {
			localData1 = value2.getData();
			localData2 = value1.getData();
		}

		/*
		 * Allocate 2D array as suggested in the homework. Create a
		 * two-dimensional array of digits. The number of rows should equal the
		 * number of digits of value1 and the length of each row should be one
		 * more than the number of digits of value1 .
		 */
		byte[][] mulStore = new byte[localData1.length][localData1.length + 1];

		int i, j, k;

		/*
		 * Fill row i with the result of multiplying value1 by the ith digit of
		 * value2 .
		 */
		for (i = 0; (i < localData1.length) && (i < localData2.length); ++i) {
			fillArray(mulStore[i], 0, (byte) 0);
			mulRow(localData1, localData2[i], mulStore[i]);
		}

		byte[] resStore = new byte[localData1.length + localData2.length];

		for (i = 0; i < resStore.length; ++i) {
			for (j = 0; j < localData1.length; ++j) {
				if ((i + j) < resStore.length && i < localData1.length) {
					resStore[i + j] += mulStore[i][j];
					if (resStore[i + j] > 9) {
						int carry;
						carry = (resStore[i + j] / 10);
						resStore[i + j] = (byte) (resStore[i + j] % 10);
						boolean done = false;
						for (k = i + j + 1; k < resStore.length && !done; ++k) {
							resStore[k] += carry;
							if (resStore[k] > 9) {
								carry = (resStore[k] / 10);
								resStore[k] = (byte) (resStore[k] % 10);
							}
							else {
								done = true;
							}
						}
					}
				}
			}
		}

		boolean negative = false;

		if (value1.isNegative() && value2.isNegative())
			negative = false;
		else if (value1.isNegative() || value2.isNegative())
			negative = true;

		// Truncate and round the exact result to the correct output precision
		int precision1 = value1.getPrecision();
		int precision2 = value2.getPrecision();
		int maxPrecision;

		if (precision1 > precision2)
			maxPrecision = precision1;
		else
			maxPrecision = precision2;

		round(resStore, localData1.length + localData2.length,
				value1.getPrecision() + value2.getPrecision(), maxPrecision);

		return new RealNumber(maxPrecision, negative, resStore);

	}

	/**
	 * This is a method for divide value 1 and value 2
	 * 
	 * @param value1
	 *            First Real Number 1
	 * @param value1
	 *            First Real Number 2
	 * @return Resultant real number with precision of larger number
	 */
	public static RealNumber divide(RealNumber value1, RealNumber value2) {

		byte[] localData1 = value1.getData(); // Reference to data in value 1
		byte[] localData2 = value2.getData(); // Reference to data in value 2
		int precision1 = value1.getPrecision(); // Value 1 precsison
		int precision2 = value2.getPrecision(); // Value 2 precsison

		int maxPrecision; // Max precsison of two values

		RealNumber zero = new RealNumber(0, "0"); // A real number to represent
													// 0

		// If value2 is zero throw exception
		if (compare(value2, zero) == 0)
			throw new ArithmeticException();

		// If value1 is zero then result is 0
		if (compare(value1, zero) == 0)
			return zero;

		if (precision1 > precision2)
			maxPrecision = precision1;
		else
			maxPrecision = precision2;

		// Follow steps as per the homework sheet

		// Shift value2 so that first no zero digit is immediately right of
		// decimal point
		int i;
		boolean done = false;

		for (i = localData2.length - 1; i >= 0 && !done; --i) {
			if (localData2[i] != 0)
				done = true;
		}
		int nonZeroIndex = i + 1;
		int shift = nonZeroIndex - precision2 + 1;

		// Create a local value2 object
		RealNumber _value2 = new RealNumber(precision2 + shift, false,
				localData2);
		;
		byte[] _localData2 = _value2.getData(); // A reference to our local
												// value2 data

		int mulFactor = 1; // How much multiplication factor we need to apply to
							// value1, which applied to value2

		// Until that first non-zero digit of value2 is 5 or larger, multiply
		// value2 by 2.
		if (_localData2[nonZeroIndex] < 5) {
			done = false;
			RealNumber two = new RealNumber(0, "2"); // Real number to represent
														// 2

			while (!done) {
				_value2 = multiply(_value2, two);
				byte[] tempData = _value2.getData();
				mulFactor = mulFactor * 2;

				if (tempData[nonZeroIndex] >= 5)
					done = true;
				;
			}
		}

		/*
		 * Whatever changes were made to value2 , be sure to do the same to
		 * value1
		 */
		RealNumber _value1 = new RealNumber(precision1 + shift, false,
				localData1); // Our local value1 object

		RealNumber _mulReal = new RealNumber(0, false,
				new byte[] { (byte) mulFactor });
		// We apply the multiplication factor to value1 applied to value2
		_value1 = multiply(_value1, _mulReal);

		/*
		 * Create a "guess" x = 2.823529 - 1.882352 * value2 where x has twice
		 * the precision of the eventual output
		 */
		int xPrecision; // precision of x
		if ((maxPrecision * 2) < 6)
			xPrecision = 6;
		else
			xPrecision = maxPrecision * 2;
		// the following step first guess = 2.823529 - 1.882352 * value2
		RealNumber op1 = new RealNumber(xPrecision, "2.823529");
		RealNumber op2 = new RealNumber(xPrecision, "1.882352");
		RealNumber x = subtract(op1, multiply(op2, _value2));

		// The terminating difference
		RealNumber terminateDiff = new RealNumber(11, false,
				new byte[] { (byte) maxPrecision });
		boolean continueLoop = true;
		RealNumber two = new RealNumber(0, "2"); // A real number 2

		while (continueLoop) {
			// save x as old_x
			RealNumber old_x = x;

			// create a "better guess" x = old_x * (2 - old_x * value2 )
			x = multiply(old_x, subtract(two, multiply(old_x, _value2)));

			RealNumber diff;
			if (compare(x, old_x) > 0)
				diff = subtract(x, old_x);
			else
				diff = subtract(old_x, x);

			if (compare(diff, terminateDiff) <= 0)
				continueLoop = false;
		}
		;

		/*
		 * Multiply x times value1 and truncate and round the result to the
		 * desired precision
		 */
		RealNumber _result = multiply(_value1, x);
		byte[] resultData = _result.getData();
		// Round the result
		round(resultData, resultData.length, _result.getPrecision(),
				maxPrecision);

		boolean negative = false;

		// Adjust the sign
		if (value1.isNegative() && value2.isNegative())
			negative = false;
		else if (value1.isNegative() || value2.isNegative())
			negative = true;

		RealNumber result = new RealNumber(maxPrecision, negative, resultData);

		return result;
	}

	/**
	 * This is a method to get square root
	 * 
	 * @param value
	 *            First Real Number 1
	 * @return Resultant real number with precision of larger number
	 */
	public static RealNumber squareRoot(RealNumber value) {
		RealNumber zero = new RealNumber(0, "0"); // A real number 0

		// Follow steps as per home work sheet

		// If the value is 0 throw exception
		if (compare(value, zero) < 0)
			throw new ArithmeticException();

		int precision = value.getPrecision(); // precision of value

		/*
		 * Create a "guess" x where x has twice the precision as value . If
		 * value > 1, set x = value , and if value < 1, set x = 1.
		 */
		RealNumber one = new RealNumber(0, "1"); // Real number 1
		RealNumber x = new RealNumber(precision * 2, "1"); // Initialize x with
															// 1.0

		// by multiplying with 1 with precision twice we increase the precision
		// of value when we assign to x
		if (compare(value, one) > 0)
			x = multiply(value, x);

		boolean continueLoop = true;
		RealNumber two = new RealNumber(0, "2");

		RealNumber terminateDiff = new RealNumber(11, false,
				new byte[] { (byte) precision });

		// Create local variables to use the formula to help debugging
		RealNumber old_x_sqr;
		RealNumber old_2x;
		RealNumber sub;
		RealNumber diff;
		RealNumber old_x;
		while (continueLoop) {
			// save x as old_x
			old_x = x;
			// create a "better guess" x = old_x - (old_x * old_x - value ) / (2
			// * old_x)
			old_x_sqr = multiply(old_x, old_x);
			old_x_sqr = subtract(old_x_sqr, value);
			old_2x = multiply(old_x, two);
			sub = divide(old_x_sqr, old_2x);
			x = subtract(old_x, sub);

			if (compare(x, old_x) > 0)
				diff = subtract(x, old_x);
			else
				diff = subtract(old_x, x);

			/*
			 * until the difference between x and old_x is less than 1/10 the
			 * desired output precision.
			 */
			if (compare(diff, terminateDiff) <= 0)
				continueLoop = false;
		}

		return x;
	}
}
