package catdany.bbb.libs;

public class ExtendedTimeSpan
{
	public int ages;
	public int years;
	public int seasons;
	public int months;
	public int weeks;
	public int days;
	public int hours;
	public int minutes;
	public int seconds;
	public int ticks;
	public long ms;
	
	public static class Builder
	{
		private final ExtendedTimeSpan span = new ExtendedTimeSpan();
		
		public Builder() {}
		
		public Builder setAges(int ages)
		{
			span.ages = ages;
			return this;
		}
		
		public Builder setYears(int years)
		{
			span.years = years;
			return this;
		}
		
		public Builder setSeasons(int seasons)
		{
			span.seasons = seasons;
			return this;
		}
		
		public Builder setMonths(int months)
		{
			span.months = months;
			return this;
		}
		
		public Builder setWeeks(int weeks)
		{
			span.weeks = weeks;
			return this;
		}
		
		public Builder setDays(int days)
		{
			span.days = days;
			return this;
		}
		
		public Builder setHours(int hours)
		{
			span.hours = hours;
			return this;
		}
		
		public Builder setMinutes(int minutes)
		{
			span.minutes = minutes;
			return this;
		}
		
		public Builder setSeconds(int seconds)
		{
			span.seconds = seconds;
			return this;
		}
		
		public Builder setTicks(int ticks)
		{
			span.ticks = ticks;
			return this;
		}
		
		public Builder setMillis(long ms)
		{
			span.ms = ms;
			return this;
		}
		
		public ExtendedTimeSpan build()
		{
			return span;
		}
	}
}