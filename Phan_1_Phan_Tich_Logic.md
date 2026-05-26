# Phần 1 - Phân tích logic tính phí vận chuyển

## 1. Quy tắc nghiệp vụ cần đáp ứng

Phí vận chuyển được tính từ hai phần:

- Phí theo cân nặng.
- Phí theo khoảng cách.

Chi tiết:

- 1kg đầu tiên tính phí cơ bản: `50.000 VND`.
- Mỗi kg tiếp theo, hoặc phần lẻ của kg tiếp theo, tính thêm `10.000 VND`.
- Khoảng cách dưới `10km` không tính thêm phí khoảng cách.
- Khoảng cách từ `10km` đến dưới `50km` tính thêm `5.000 VND/km`.
- Khoảng cách từ `50km` trở lên tính thêm `4.000 VND/km`.

## 2. Lỗi logic khi cân nặng là số lẻ

Đoạn code cũ:

```java
weightFee = 50000 + (Math.floor(weightKg - 1) * 10000);
```

Vấn đề nằm ở việc dùng `Math.floor(weightKg - 1)`.

`Math.floor` làm tròn xuống, nên phần kg lẻ sau 1kg đầu tiên bị bỏ qua. Trong khi đó, quy tắc nghiệp vụ yêu cầu mỗi kg tiếp theo hoặc phân số của kg đều phải tính thêm phí. Vì vậy cần làm tròn lên bằng `Math.ceil`.

Ví dụ với đơn hàng `1.5kg`:

```text
weightKg - 1 = 0.5
Math.floor(0.5) = 0
```

Code cũ tính:

```text
50.000 + 0 * 10.000 = 50.000 VND
```

Nhưng theo nghiệp vụ, `0.5kg` sau 1kg đầu tiên vẫn được tính là 1kg phát sinh:

```text
50.000 + 1 * 10.000 = 60.000 VND
```

Ví dụ với đơn hàng `2.3kg`:

```text
weightKg - 1 = 1.3
Math.floor(1.3) = 1
```

Code cũ tính:

```text
50.000 + 1 * 10.000 = 60.000 VND
```

Nhưng đúng ra `1.3kg` phát sinh phải làm tròn lên thành `2kg`:

```text
50.000 + 2 * 10.000 = 70.000 VND
```

Do đó công thức đúng là:

```java
weightFee = 50000 + (Math.ceil(weightKg - 1) * 10000);
```

## 3. Phân tích logic tại các ngưỡng khoảng cách

Đoạn code xử lý khoảng cách:

```java
if (distanceKm < 10) {
    distanceFee = 0;
} else if (distanceKm < 50) {
    distanceFee = distanceKm * 5000;
} else {
    distanceFee = distanceKm * 4000;
}
```

Về mặt điều kiện biên, đoạn code này tương ứng với:

- `distanceKm < 10`: không tính thêm phí.
- `10 <= distanceKm < 50`: tính `5.000 VND/km`.
- `distanceKm >= 50`: tính `4.000 VND/km`.

Đây là đúng với nghiệp vụ nếu khoảng cách được tính trực tiếp theo số km nhập vào.

Các điểm biên quan trọng:

- `9.9km`: nhỏ hơn `10km`, không tính phí khoảng cách.
- `10km`: thuộc nhóm từ `10km` đến dưới `50km`, tính `10 * 5.000 = 50.000 VND`.
- `49km`: vẫn nhỏ hơn `50km`, tính `49 * 5.000 = 245.000 VND`.
- `50km`: thuộc nhóm từ `50km` trở lên, tính `50 * 4.000 = 200.000 VND`.

Vì vậy khi kiểm thử cần có test case tại đúng `10km`, `49km` và đúng `50km` để đảm bảo code không bị sai ở ngưỡng biên.

## 4. Kết luận lỗi cần sửa

Lỗi chính nằm ở phần tính phí cân nặng:

```java
Math.floor(weightKg - 1)
```

Cách làm này làm tròn xuống nên bỏ sót phí đối với cân nặng lẻ như `1.5kg`, `2.3kg`.

Cần sửa thành:

```java
Math.ceil(weightKg - 1)
```

Phần điều kiện khoảng cách cần được giữ rõ ràng để đảm bảo:

- `10km` được tính theo mức `5.000 VND/km`.
- `49km` vẫn được tính theo mức `5.000 VND/km`.
- `50km` được tính theo mức `4.000 VND/km`.
