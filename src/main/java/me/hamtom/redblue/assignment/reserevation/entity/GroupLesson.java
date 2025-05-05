package me.hamtom.redblue.assignment.reserevation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GroupLesson {
    @Id
    private int seqGroupLesson;
    private int totalSeatNumber;
    private int occupiedNumber;
    
    public boolean isTakableLesson (){
        return 0 < (totalSeatNumber - occupiedNumber);
    }

    public void takeLesson() {
        occupiedNumber -= 1;
    }

    public static GroupLesson createGroupLesson(int seqGroupLesson, int totalSeatNumber, int occupiedNumber) {
        GroupLesson groupLesson = new GroupLesson();
        groupLesson.setSeqGroupLesson(seqGroupLesson);
        groupLesson.setTotalSeatNumber(totalSeatNumber);
        groupLesson.setOccupiedNumber(occupiedNumber);
        return groupLesson;
    }
}
