package coursehub.practice3.task15;

import coursehub.Course;

public class LimitedCourse extends Course {
    private int spaceLeft;
    private String prerequisites;
    private int minAge;
    private boolean enrollmentOpen;

    public LimitedCourse(
            long id, String title, int duration, int spaceLeft, String prerequisites, int minAge)
    {
        super(id, title, duration);
        if (spaceLeft >= 0)
            this.spaceLeft = spaceLeft;
        else
            this.spaceLeft = 0;
        if (minAge >= 0)
            this.minAge = minAge;
        else
            this.minAge = 0;
        if (prerequisites != null)
            this.prerequisites = prerequisites;
        else
            this.prerequisites = "";
        this.enrollmentOpen = true;
    }

    public int getSpaceLeft()
    {
        return spaceLeft;
    }

    public String getPrerequisites()
    {
        return prerequisites;
    }

    public int getMinAge()
    {
        return minAge;
    }

    public boolean isEnrollmentOpen()
    {
        return enrollmentOpen;
    }

    public void setSpaceLeft(int spaceLeft)
    {
        if (spaceLeft > 0)
            this.spaceLeft = spaceLeft;
        else
            spaceLeft = 0;
    }

    public void setPrerequisites(String prerequisites)
    {
        if (prerequisites != null)
            this.prerequisites = prerequisites;
        else
            this.prerequisites = "";
    }

    public void setMinAge(int minAge)
    {
        if (minAge >= 0)
            this.minAge = minAge;
        else
            this.minAge = 0;
    }

    public void setEnrollmentOpen(boolean enrollmentOpen)
    {
        this.enrollmentOpen = enrollmentOpen;
    }
}
