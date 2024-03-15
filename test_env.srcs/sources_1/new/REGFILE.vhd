----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 03/13/2023 03:39:30 PM
-- Design Name: 
-- Module Name: REGFILE - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- 
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.STD_LOGIC_UNSIGNED.ALL;
use IEEE.STD_LOGIC_ARITH.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity REGFILE is
   Port ( 
   RA1:in STD_LOGIC_VECTOR(2 downto 0);
   RA2:in STD_LOGIC_VECTOR(2 downto 0);
   clk:in STD_LOGIC;
   WA:in STD_LOGIC_VECTOR(2 downto 0);
   WD:in STD_LOGIC_VECTOR(15 downto 0);
   RD1:out STD_LOGIC_VECTOR(15 downto 0);
   RD2:out STD_LOGIC_VECTOR(15 downto 0);
   WEN:in STD_LOGIC); 
end REGFILE;

architecture Behavioral of REGFILE is
type reg_array is array (0 to 7) of std_logic_vector(15 downto 0);
signal reg_file:reg_array:=(
x"0000",
x"0000",
x"0000",
x"0000",
x"0000",
x"0011",
x"0101",
others=>x"0000");
begin
process(clk)
begin
if rising_edge(clk) then
if wen='1' then
reg_file(conv_integer(wa))<=wd;
end if;
end if;
end process;
rd1<=reg_file(conv_integer(ra1));
rd2<=reg_file(conv_integer(ra2));

end Behavioral;
